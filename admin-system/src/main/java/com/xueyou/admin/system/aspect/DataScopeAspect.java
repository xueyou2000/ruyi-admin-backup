package com.xueyou.admin.system.aspect;

import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.annotation.DataScope;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.redis.util.RedisUtils;
import com.xueyou.admin.system.domain.Role;
import com.xueyou.admin.system.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 数据过滤处理
 *
 * @author xueyou
 * @date 2020/12/30
 */
@Aspect
@Slf4j
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    // 配置织入点
    @Pointcut("@annotation(com.xueyou.admin.common.core.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            // 超级管理员不过滤
            if (!TrueOrFalse.TRUE.equals(currentUser.getAdmin())) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias(), controllerDataScope.paramsIndex());
            }
        } else {
            log.warn("数据权限拦截失败,执行对象 currentUser is null");
        }
    }

    private static User getCurrentUser() {
        // 获取当前的用户
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        return RedisUtils.get(Constants.ACCESS_TOKEN + token, User.class);
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param deptAlias 别名
     * @param userAlias 别名
     * @param paramsIndex   实体参数位置
     */
    public static void dataScopeFilter(JoinPoint joinPoint, User user, String deptAlias, String userAlias, long paramsIndex) {
        BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[(int) paramsIndex];
        baseEntity.getParams().put(DATA_SCOPE, getDataScopeFilterSql(user, deptAlias, userAlias));
    }

    /**
     * 获取数据过滤的注入sql
     */
    public static String getDataScopeFilterSql(User user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();
        for (Role role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias, role.getRoleId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                String deptChild = user.getDept().getParentId() + "," + user.getDeptId();
                sqlString.append(StringUtils.format(" OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or ancestors LIKE '%{}%' )", deptAlias, user.getDeptId(), deptChild));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                } else {
                    sqlString.append(StringUtils.format(" OR {}.dept_id IS NULL ", deptAlias));
                }
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())) {
            return " AND (" + sqlString.substring(4) + ")";
        }
        return sqlString.toString();
    }

    /**
     * 获取数据过滤的注入sql
     */
    public static String getDataScopeFilterSql(String deptAlias, String userAlias) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return getDataScopeFilterSql(currentUser, deptAlias, userAlias);
        } else {
            return "";
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

}
