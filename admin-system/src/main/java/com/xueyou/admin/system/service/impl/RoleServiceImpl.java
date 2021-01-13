package com.xueyou.admin.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyou.admin.common.core.annotation.DataScope;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.system.aspect.DataScopeAspect;
import com.xueyou.admin.system.domain.Role;
import com.xueyou.admin.system.domain.RoleDept;
import com.xueyou.admin.system.domain.RoleMenu;
import com.xueyou.admin.system.domain.UserRole;
import com.xueyou.admin.system.mapper.RoleDeptMapper;
import com.xueyou.admin.system.mapper.RoleMapper;
import com.xueyou.admin.system.mapper.RoleMenuMapper;
import com.xueyou.admin.system.mapper.UserRoleMapper;
import com.xueyou.admin.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 角色 业务处理层
 *
 * @author xueyou
 * @date 2020-12-30
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleDeptMapper roleDeptMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 分页查询角色
     */
    @Override
    @DataScope(deptAlias = "d", paramsIndex = 1)
    public IPage<Role> selectRoleByPage(IPage<Role> page, Role role) {
        return roleMapper.selectRoleByPage(page, role);
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRoleAll(DataScopeAspect.getDataScopeFilterSql("d", ""));
    }

    /**
     * 新增角色
     *
     * @param role 角色
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(Role role) {
        save(role);
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(Role role) {
        updateById(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(Role role) {
        long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        return info == null || info.getRoleId() == roleId;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(Role role) {
        long roleId = role.getRoleId() == null ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        return info == null || info.getRoleId() == roleId;
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(String userId) {
        List<Role> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(Role role) {
        // 修改角色信息
        updateById(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(Role role) {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<RoleDept> list = new ArrayList<>();
        for (Long deptId : role.getDeptIds()) {
            RoleDept rd = new RoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public int deleteAuthUser(UserRole userRole) {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int deleteAuthUsers(Long roleId, String[] userIds) {
        return userRoleMapper.deleteUserRoleInfos(roleId, userIds);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, String[] userIds) {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<>();
        for (String userId : userIds) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }

}