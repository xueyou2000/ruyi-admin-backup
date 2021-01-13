package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 登陆记录数据访问层
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 11:38 上午
 */
@Mapper
public interface LoginInfoMapper extends BaseMapper<LoginInfo> {

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginInfo();

}
