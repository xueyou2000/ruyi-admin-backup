package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.Menu;
import com.xueyou.admin.system.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单数据访问层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 2:41 下午
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询权限列表
     *
     * @param userId    用户id
     */
    List<String> selectPermsByUserId(String userId);

    /**
     * 查询系统菜单 (不含按钮)
     */
    List<Menu> selectMenuNormalAll();

    /**
     * 查询指定用户系统菜单 (不含按钮)
     *
     * @param userId 用户id
     */
    List<Menu> selectMenusByUserId(String userId);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Menu> selectMenuIdsByRoleId(Long roleId);

}
