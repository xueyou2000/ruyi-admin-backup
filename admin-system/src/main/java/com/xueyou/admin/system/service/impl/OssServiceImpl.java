package com.xueyou.admin.system.service.impl;

import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.system.domain.Oss;
import com.xueyou.admin.system.mapper.OssMapper;
import com.xueyou.admin.system.service.OssService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件 业务处理层
 *
 * @author xueyou
 * @date 2020-12-28
 */
@Service
public class OssServiceImpl extends BaseServiceImpl<OssMapper, Oss> implements OssService {

    @Resource
    private OssMapper ossMapper;

}