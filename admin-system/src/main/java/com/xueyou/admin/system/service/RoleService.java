package com.xueyou.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.Role;
import com.xueyou.admin.system.domain.UserRole;

import java.util.List;
import java.util.Set;

/**
 * 角色 业务层
 *
 * @author xueyou
 * @date 2020-12-30
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 分页查询角色
     */
    IPage<Role> selectRoleByPage(IPage<Role> page, Role role);

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> selectRoleAll();

    /**
     * 新增角色
     *
     * @param role 角色
     * @return 结果
     */
    int insertRole(Role role);

    /**
     * 修改角色
     *
     * @param role 角色
     * @return 结果
     */
    int updateRole(Role role);


    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleNameUnique(Role role);

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean checkRoleKeyUnique(Role role);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRoleKeys(String userId);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int authDataScope(Role role);

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    int deleteAuthUser(UserRole userRole);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, String[] userIds);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUsers(Long roleId, String[] userIds);

}