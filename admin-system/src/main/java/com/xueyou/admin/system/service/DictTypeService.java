package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.DictType;

import java.util.List;

/**
 * 字典类型 业务层
 *
 * @author xueyou
 */
public interface DictTypeService extends BaseService<DictType> {

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<DictType> selectDictTypeAll();

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    DictType selectDictTypeById(Long dictId);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    boolean updateDictType(DictType dictType);

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     * @throws Exception 异常
     */
    boolean deleteDictTypeByIds(String ids) throws Exception;

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    Boolean checkDictTypeUnique(DictType dict);

}
