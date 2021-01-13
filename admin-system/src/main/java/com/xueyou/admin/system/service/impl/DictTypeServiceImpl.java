package com.xueyou.admin.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.xueyou.admin.common.core.exception.BusinessException;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.common.core.utils.StringUtils;
import com.xueyou.admin.system.domain.DictType;
import com.xueyou.admin.system.mapper.DictDataMapper;
import com.xueyou.admin.system.mapper.DictTypeMapper;
import com.xueyou.admin.system.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 字典类型 业务处理层
 *
 * @author xueyou
 * @date 2020/12/21
 */
@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictDataMapper dictDataMapper;


    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public boolean deleteDictTypeByIds(String ids) {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            DictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return removeByIds(Arrays.asList(dictIds));
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    public boolean updateDictType(DictType dictType) {
        DictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return updateById(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果   true=唯一, false=不唯一
     */
    @Override
    public Boolean checkDictTypeUnique(DictType dict) {
        long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        return !StringUtils.isNotNull(dictType) || dictType.getDictId() == dictId;
    }

}
