package com.xueyou.admin.service;

import com.xueyou.admin.model.vo.TokenInfo;
import com.xueyou.admin.system.domain.User;

/**
 * 授权token
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:36 下午
 */
public interface AccessTokenService {

    /**
     * 查询登陆用户通过token
     *
     * @param token 授权token
     */
    User queryByToken(String token);

    /**
     * 生成Token
     *
     * @param user  用户信息
     */
    TokenInfo createToken(User user);

    /**
     * 删除Token
     *
     * @param userId 用户id
     */
    void expireTokenByUserId(String userId);

    /**
     * 删除Token
     *
     * @param token 授权token
     */
    void expireToken(String token);

}
