package com.xueyou.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.User;

/**
 * 用户业务层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 5:59 下午
 */
public interface UserService extends BaseService<User> {

    /**
     * 分页查询用户
     */
    IPage<User> selectUserByPage(IPage<User> page, User user);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     */
    User selectUserById(String userId);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     */
    User selectUserByLoginName(String userName);

    /**
     * 通过手机号查询用户
     *
     * @param mobile    手机号
     */
    User selectUserByMobile(String mobile);

    /**
     * 更新用户信息
     *
     * @param user  用户信息
     */
    boolean updateUser(User user);

    /**
     * 新增用户
     */
    boolean insertUser(User user);

    /**
     * 重建用户与角色关联表
     *
     * @param user  用户信息
     */
    void insertUserRole(User user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    boolean checkLoginNameUnique(String loginName);

    /**
     * 校验手机号是否唯一
     *
     * @param phonenumber 手机号
     * @return 结果
     */
    boolean checkPhonenUnique(String phonenumber);

    /**
     * 重置密码
     */
    boolean restPassword(String userId, String newPassword);

    /**
     * 更改用户状态
     */
    boolean changeStatus(String userId, String status);

}
