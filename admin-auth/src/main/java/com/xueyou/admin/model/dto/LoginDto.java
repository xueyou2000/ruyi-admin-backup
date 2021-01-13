package com.xueyou.admin.model.dto;

import com.anji.captcha.model.vo.CaptchaVO;
import lombok.Data;

/**
 * 登陆请求
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/10 3:26 下午
 */
@Data
public class LoginDto {

    /**
     * 登陆用户
     */
    private String username;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 滑块验证码二次验证参数
     */
    private CaptchaVO captchaVO;

}
