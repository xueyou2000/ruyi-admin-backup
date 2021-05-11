package com.xueyou.admin.system.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xueyou.admin.common.core.annotation.DataScope;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.redis.annotation.RedisCache;
import com.xueyou.admin.system.domain.Menu;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.mapper.MenuMapper;
import com.xueyou.admin.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单业务层实现
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:38 下午
 */
@Service
@Slf4j
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户id
     * @return 权限列表
     */
    @Override
    @RedisCache(key = "user_perms", fieldKey = "#userId")
    public Set<String> selectPermsByUserId(String userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param user 用户
     * @return 菜单列表
     */
    @Override
    public List<Menu> selectMenusByUser(User user) {
        List<Menu> menus;
        if (TrueOrFalse.TRUE.equals(user.getAdmin())) {
            menus = menuMapper.selectMenuNormalAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getUserId());
        }
        return menus;
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return menuMapper.selectMenuList(menu);
    }

    @Override
    public List<Menu> selectMenuPermissionsList(User user) {
        List<Menu> menus;
        if (TrueOrFalse.TRUE.equals(user.getAdmin())) {
            menus = lambdaQuery().eq(Menu::getVisible, "0").list();
        } else {
            menus = menuMapper.selectMenuPermissionsList(user.getUserId());
        }
        return menus;
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单Id
     */
    @Override
    public boolean deleteMenuById(Long menuId) {
        LambdaQueryChainWrapper<Menu> query = lambdaQuery();
        query.eq(Menu::getMenuId, menuId)
                .or()
                .eq(Menu::getParentId, menuId);
        return remove(query.getWrapper());
    }

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public List<Menu> selectMenuIdsByRoleId(Long roleId) {
        return menuMapper.selectMenuIdsByRoleId(roleId);
    }

}
