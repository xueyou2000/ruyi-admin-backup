package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.Menu;
import com.xueyou.admin.system.domain.User;

import java.util.List;
import java.util.Set;

/**
 * 菜单业务层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:35 下午
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId    用户id
     * @return  权限列表
     */
    Set<String> selectPermsByUserId(String userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param user    用户
     * @return  菜单列表
     */
    List<Menu> selectMenusByUser(User user);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * 删除菜单
     *
     * @param menuId    菜单Id
     */
    boolean deleteMenuById(Long menuId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Menu> selectMenuIdsByRoleId(Long roleId);

}
