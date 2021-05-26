package com.xueyou.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyou.admin.common.core.annotation.DataScope;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import com.xueyou.admin.common.core.exception.auth.UnauthorizedException;
import com.xueyou.admin.common.core.exception.base.BusinessRuntimeException;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.system.aspect.DataScopeAspect;
import com.xueyou.admin.system.domain.Dept;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.mapper.DeptMapper;
import com.xueyou.admin.system.service.DeptService;
import com.xueyou.admin.system.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 部门 业务处理层
 *
 * @author xueyou
 * @date 2020-12-29
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 分页查询部门
     */
    @Override
    @DataScope(deptAlias = "d", paramsIndex = 1)
    public IPage<Dept> selectDeptByPage(IPage<Dept> page, Dept dept) {
        return deptMapper.selectDeptByPage(page, dept);
    }

    /**
     * 查询部门列表
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Dept> selectDeptList(Dept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询关联部门列表
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Dept> selectRelationDeptList(Dept dept) {
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException();
        }
        Dept currentDept = deptMapper.selectById(currentUser.getDeptId());
        return deptMapper.selectRelationDeptList(dept, currentDept.getAncestors());
    }

    /**
     * 分页查询关联部门
     */
    @Override
    @DataScope(deptAlias = "d", paramsIndex = 1)
    public IPage<Dept> selectRelationDeptList(IPage<Dept> page, Dept dept) {
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException();
        }
        if (TrueOrFalse.TRUE.equals(currentUser.getAdmin())) {
            return selectDeptByPage(page, dept);
        }
        Dept currentDept = deptMapper.selectById(currentUser.getDeptId());
        return deptMapper.selectRelationDeptByPage(page, dept, currentDept.getAncestors());
    }

    /**
     * 查询部门列表
     */
    @Override
    public List<Dept> selectDeptAll() {
        return deptMapper.selectDeptAll(DataScopeAspect.getDataScopeFilterSql("d", ""));
    }

    /**
     * 新增部门
     *
     * @param dept 部门
     * @return 结果
     */
    @Override
    public boolean insertDept(Dept dept) {
        if (dept.getParentId() > 0) {
            Dept parentDept = deptMapper.selectById(dept.getParentId());
            // 如果父节点不为"正常"状态,则不允许新增子节点
            if (!"0".equals(parentDept.getStatus())) {
                throw new BusinessRuntimeException("父部门停用，无法新增");
            }
            dept.setAncestors(parentDept.getAncestors() + "," + dept.getParentId());
        }
        return save(dept);
    }

    /**
     * 修改部门
     *
     * @param dept 部门
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(Dept dept) {
        Dept parentDept = deptMapper.selectById(dept.getParentId());
        Dept oldDept = deptMapper.selectById(dept.getDeptId());
        if (parentDept != null && oldDept != null) {
            String newAncestors = parentDept.getAncestors() + "," + parentDept.getParentId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }

        boolean result = updateById(dept);
        if ("0".equals(dept.getStatus())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptNameUnique(Dept dept) {
        long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        Dept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        return !StringUtils.isNotNull(info) || info.getDeptId() == deptId;
    }

    /**
     * 根据角色ID查询部门编号
     *
     * @param roleId 角色编号
     */
    @Override
    public Set<Long> roleDeptIds(Long roleId) {
        return deptMapper.selectRoleDeptIds(roleId);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<Dept> children = deptMapper.selectChildrenDeptById(deptId);
        for (Dept child : children) {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept) {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectById(dept.getParentId());
        if (dept != null) {
            dept.setUpdateBy(updateBy);
            deptMapper.updateDeptStatus(dept);
        }
    }

}