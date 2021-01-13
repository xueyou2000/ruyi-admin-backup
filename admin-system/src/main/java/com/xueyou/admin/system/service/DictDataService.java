package com.xueyou.admin.system.service;

import com.xueyou.admin.common.core.entity.Dict;
import com.xueyou.admin.common.core.service.BaseService;
import com.xueyou.admin.system.domain.DictData;

import java.util.List;
import java.util.Map;

/**
 * 字典数据 业务层
 *
 * @author xueyou
 */
public interface DictDataService extends BaseService<DictData> {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<DictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * 获取字典数据
     *
     * @param types 字典类型集合
     */
    Map<String, List<DictData>> findByTypes(String[] types);

    /**
     * 获取字典数据
     *
     * @param types 字典类型集合
     */
    Map<String, List<Dict>> findDictByTypes(String[] types);

}
