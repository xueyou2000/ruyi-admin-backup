package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统访问记录
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:36 上午
 */
@Data
@TableName("sys_login_info")
public class LoginInfo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long infoId;

    /**
     * 登陆用户账号
     */
    private String loginName;

    /**
     * 登陆用户ID
     */
    private String userId;

    /**
     * 登录状态 (0成功, 1失败)
     */
    private String status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private LocalDateTime loginTime;


}
