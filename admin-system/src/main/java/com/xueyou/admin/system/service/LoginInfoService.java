package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.LoginInfo;

import java.util.List;

/**
 * 系统登陆日志
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:55 上午
 */
public interface LoginInfoService extends BaseService<LoginInfo> {

    /**
     * 查询系统登陆日志集合
     *
     * @param loginInfo 登陆日志信息
     */
    List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo);

    /**
     * 晴空系统登陆日志
     */
    void cleanLoginInfo();

}
