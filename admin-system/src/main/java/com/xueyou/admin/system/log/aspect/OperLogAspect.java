package com.xueyou.admin.system.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.common.core.utils.AddressUtils;
import com.xueyou.admin.common.core.utils.IpUtils;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.common.core.utils.spring.SpringContextHolder;
import com.xueyou.admin.system.domain.OperLog;
import com.xueyou.admin.system.log.enums.BusinessStatus;
import com.xueyou.admin.system.log.event.SysOperLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作日志记录切面
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:05 下午
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.xueyou.admin.system.log.annotation.OperLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        logHandle(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        logHandle(joinPoint, e);
    }

    /**
     * 处理操作日志
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    protected void logHandle(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获取注解
            com.xueyou.admin.system.log.annotation.OperLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            OperLog operLog = new OperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());

            // 请求ip
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
            String username = request.getHeader(Constants.CURRENT_USERNAME);
            operLog.setOperName(username);

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
        } catch (Exception exp) {
            log.error("记录操作日志异常: {}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取方法上的 OperLog 注解信息
     *
     * @param joinPoint 切点
     */
    private com.xueyou.admin.system.log.annotation.OperLog getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(com.xueyou.admin.system.log.annotation.OperLog.class);
        }
        return null;
    }

    /**
     * 设置操作日志参数
     *
     * @param log       操作日志注解
     * @param operLog   操作日志
     * @param args      参数
     */
    public void getControllerMethodDescription(com.xueyou.admin.system.log.annotation.OperLog log, OperLog operLog, Object[] args) {
        // 设置操作动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存请求参数和直
        if (log.isSaveRequestData()) {
            setRequestValue(operLog, args);
        }
    }

    /**
     * 设置请求参数
     *
     * @param operLog   操作日志注解
     * @param args      参数
     */
    private void setRequestValue(OperLog operLog, Object[] args) {
        // 排除 ServletResponse 参数
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse))
                .collect(Collectors.toList());
        String params = JSON.toJSONString(param, true);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

}
