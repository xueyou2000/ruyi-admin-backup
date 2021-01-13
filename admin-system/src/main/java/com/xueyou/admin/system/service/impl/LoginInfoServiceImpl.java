package com.xueyou.admin.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.system.domain.LoginInfo;
import com.xueyou.admin.system.mapper.LoginInfoMapper;
import com.xueyou.admin.system.service.LoginInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.xueyou.admin.service.impl
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:59 上午
 */
@Service
public class LoginInfoServiceImpl extends BaseServiceImpl<LoginInfoMapper, LoginInfo> implements LoginInfoService {

    @Resource
    private LoginInfoMapper loginInfoMapper;

    /**
     * 查询系统登陆日志集合
     *
     * @param loginInfo 登陆日志信息
     */
    @Override
    public List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo) {
        return loginInfoMapper.selectLoginInfoList(loginInfo);
    }

    /**
     * 晴空系统登陆日志
     */
    @Override
    public void cleanLoginInfo() {
        loginInfoMapper.cleanLoginInfo();
    }
}
