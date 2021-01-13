package com.xueyou.admin.model.dto;

import lombok.Data;

/**
 * 手机验证码登陆请求
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/19 10:38 上午
 */
@Data
public class MobileLoginDto {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String captcha;

}
