package com.xueyou.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.xueyou.admin.common.core.annotation.DataScope;
import com.xueyou.admin.common.core.exception.BusinessException;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.common.core.utils.RandomUtils;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.domain.UserRole;
import com.xueyou.admin.system.mapper.UserMapper;
import com.xueyou.admin.system.mapper.UserRoleMapper;
import com.xueyou.admin.system.service.UserService;
import com.xueyou.admin.system.utils.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务层实现
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 6:00 下午
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 分页查询用户
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u", paramsIndex = 1)
    public IPage<User> selectUserByPage(IPage<User> page, User user) {
        return userMapper.selectUserByPage(page, user);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(String userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     */
    @Override
    public User selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号查询用户
     *
     * @param mobile 手机号
     */
    @Override
    public User selectUserByMobile(String mobile) {
        return userMapper.selectUserByPhoneNumber(mobile);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(user.getUserId());
        // 重建用户与角色关联
        insertUserRole(user);
        return updateById(user);
    }

    /**
     * 新增用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUser(User user) {
        // 新增用户与角色管理
        insertUserRole(user);
        return save(user);
    }

    /**
     * 重建用户与角色关联表
     *
     * @param user 用户信息
     */
    @Override
    public void insertUserRole(User user) {
        List<Long> roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            List<UserRole> list = new ArrayList<>();
            for (Long roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                list.add(userRole);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    @Override
    public boolean checkLoginNameUnique(String loginName) {
        return lambdaQuery().eq(User::getLoginName, loginName).count() == 0;
    }

    /**
     * 校验手机号是否唯一
     *
     * @param phonenumber 手机号
     * @return 结果
     */
    @Override
    public boolean checkPhonenUnique(String phonenumber) {
        return lambdaQuery().eq(User::getPhonenumber, phonenumber).count() == 0;
    }

    /**
     * 重置密码
     *
     * @param userId        用户id
     * @param newPassword   新密码
     */
    @Override
    public boolean restPassword(String userId, String newPassword) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setSalt(RandomUtils.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
        return updateById(user);
    }

    /**
     * 更改用户状态
     */
    @Override
    public boolean changeStatus(String userId, String status) {
        return lambdaUpdate()
                .set(User::getStatus, status)
                .eq(User::getUserId, userId).update();
    }


}
