package com.xueyou.admin.model.vo;

import lombok.Data;

/**
 * 授权Token信息
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:43 下午
 */
@Data
public class TokenInfo {

    /** 用户ID **/
    private String userId;

    /** 授权token **/
    private String token;

    /** 过期时间(秒) **/
    private long expire;

}
