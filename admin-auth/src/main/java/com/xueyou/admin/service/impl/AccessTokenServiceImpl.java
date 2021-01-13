package com.xueyou.admin.service.impl;

import com.xueyou.admin.common.core.utils.UserAgentutils;
import com.xueyou.admin.common.core.utils.spring.ServletUtils;
import com.xueyou.admin.model.vo.TokenInfo;
import com.xueyou.admin.service.AccessTokenService;
import com.xueyou.admin.common.core.Constants;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.common.core.utils.CodeBuilder;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.common.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 授权token实现
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:46 下午
 */
@Slf4j
@Service("accessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {

    /**
     * 6小时后过期
     */
    private final static long EXPIRE = 6 * 60 * 60;

    private final static String ACCESS_TOKEN = Constants.ACCESS_TOKEN;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    /**
     * 查询登陆用户通过token
     *
     * @param token 授权token
     */
    @Override
    public User queryByToken(String token) {
        return RedisUtils.get(ACCESS_TOKEN + token, User.class);
    }

    /**
     * 生成Token
     *
     * @param user 用户信息
     */
    @Override
    @CacheEvict(value = "user_token", key = "#user.userId")
    public TokenInfo createToken(User user) {
        String token = CodeBuilder.build();
        TokenInfo res = new TokenInfo();
        res.setToken(token);
        res.setUserId(user.getUserId());
        res.setExpire(EXPIRE);

        user.setUserAgent(UserAgentutils.getUserAgent(ServletUtils.getRequest()));
        RedisUtils.setEx(ACCESS_TOKEN + token, user, (int) EXPIRE);
        RedisUtils.setEx(ACCESS_USERID + user.getUserId(), token, (int) EXPIRE);
        return res;
    }

    /**
     * 删除Token
     *
     * @param userId 用户id
     */
    @Override
    public void expireTokenByUserId(String userId) {
        String token = RedisUtils.get(ACCESS_USERID + userId, String.class);
        if (StringUtils.isNotBlank(token)) {
            RedisUtils.del(ACCESS_USERID + userId);
            RedisUtils.del(ACCESS_TOKEN + token);
        }
    }

    /**
     * 删除Token
     *
     * @param token 授权token
     */
    @Override
    public void expireToken(String token) {
        User user = queryByToken(token);
        if (StringUtils.isNotBlank(token)) {
            RedisUtils.del(ACCESS_USERID + user.getUserId());
            RedisUtils.del(ACCESS_TOKEN + token);
        }
    }
}
