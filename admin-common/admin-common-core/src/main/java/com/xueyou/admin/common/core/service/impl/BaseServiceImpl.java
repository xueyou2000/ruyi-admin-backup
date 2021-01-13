package com.xueyou.admin.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyou.admin.common.core.dto.query.QueryBaseDto;
import com.xueyou.admin.common.core.enums.SortDirection;
import com.xueyou.admin.common.core.utils.QueryUtils;
import com.xueyou.admin.common.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 基础Service实现
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 9:25 上午
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {


    /**
     * 分页查询(单表)
     *
     * @param page     分页信息
     * @param data     查询实体信息
     * @param queryDto 查询高级信息
     */
    @Override
    public IPage<T> queryByPage(IPage<T> page, T data, QueryBaseDto queryDto) {
        QueryWrapper<T> queryWrapper = QueryUtils.createPredicate(data, queryDto);
        if (SortDirection.DESC.equals(queryDto.getDirection())) {
            queryWrapper.orderByDesc(queryDto.getSortNames().toArray(new String[0]));
        } else {
            queryWrapper.orderByAsc(queryDto.getSortNames().toArray(new String[0]));
        }
        return page(page,queryWrapper);
    }

    /**
     * 分页查询(单表)
     *
     * @param page 分页信息
     * @param data 查询实体信息
     */
    @Override
    public IPage<T> queryByPage(IPage<T> page, T data) {
        return queryByPage(page, data, new QueryBaseDto());
    }

    /**
     * 查询集合
     *
     * @param data     查询实体信息
     * @param queryDto 查询高级信息
     */
    @Override
    public List<T> queryAll(T data, QueryBaseDto queryDto) {
        QueryWrapper<T> queryWrapper = QueryUtils.createPredicate(data, queryDto);
        if (SortDirection.DESC.equals(queryDto.getDirection())) {
            queryWrapper.orderByDesc(queryDto.getSortNames().toArray(new String[0]));
        } else {
            queryWrapper.orderByAsc(queryDto.getSortNames().toArray(new String[0]));
        }
        return list(queryWrapper);
    }

    /**
     * 查询集合
     *
     * @param data 查询实体信息
     */
    @Override
    public List<T> queryAll(T data) {
        return queryAll(data, new QueryBaseDto());
    }
}
