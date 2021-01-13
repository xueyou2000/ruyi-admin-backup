package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import com.xueyou.admin.system.domain.User;

/**
 * 用户数据访问曾
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 2:46 下午
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页查询用户
     */
    IPage<User> selectUserByPage(IPage<User> page, User user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    User selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    User selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    User selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    User selectUserById(String userId);

}
