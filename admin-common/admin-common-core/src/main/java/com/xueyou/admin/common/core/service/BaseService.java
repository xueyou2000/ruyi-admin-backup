package com.xueyou.admin.common.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyou.admin.common.core.dto.query.QueryBaseDto;

import java.util.List;

/**
 * 基础Service
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 9:24 上午
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 分页查询(单表)
     *
     * @param page  分页信息
     * @param data  查询实体信息
     * @param queryDto  查询高级信息
     */
    IPage<T> queryByPage(IPage<T> page, T data, QueryBaseDto queryDto);

    /**
     * 分页查询(单表)
     *
     * @param page  分页信息
     * @param data  查询实体信息
     */
    IPage<T> queryByPage(IPage<T> page, T data);

    /**
     * 查询集合
     *
     * @param data  查询实体信息
     * @param queryDto  查询高级信息
     */
    List<T> queryAll(T data, QueryBaseDto queryDto);

    /**
     * 查询集合
     *
     * @param data  查询实体信息
     */
    List<T> queryAll(T data);

}
