package com.xueyou.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyou.admin.system.domain.DictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典类型数据访问层
 *
 * @author xueyou
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

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
     * 通过字典ID删除字典信息
     *
     * @param dictId 字典ID
     * @return 结果
     */
    int deleteDictTypeById(Long dictId);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    DictType checkDictTypeUnique(String dictType);

}
