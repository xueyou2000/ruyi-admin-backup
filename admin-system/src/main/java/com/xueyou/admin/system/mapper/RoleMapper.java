package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyou.admin.system.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色 数据访问层
 *
 * @author xueyou
 * @date 2020-12-30
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询角色
     */
    IPage<Role> selectRoleByPage(IPage<Role> page, Role role);

    /**
     * 查询角色列表
     */
    List<Role> selectRoleAll(String dataScope);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolesByUserId(String userId);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    Role selectRoleById(Long roleId);

    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    Role checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    Role checkRoleKeyUnique(String roleKey);


}