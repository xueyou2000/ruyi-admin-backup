package com.xueyou.admin.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.xueyou.admin.common.core.entity.Dict;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import com.xueyou.admin.common.core.service.impl.BaseServiceImpl;
import com.xueyou.admin.system.domain.DictData;
import com.xueyou.admin.system.mapper.DictDataMapper;
import com.xueyou.admin.system.service.DictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据 业务处理层
 *
 * @author xueyou
 * @date 2020/12/21
 */
@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Resource
    private DictDataMapper dictDataMapper;

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return lambdaQuery()
                .eq(DictData::getDictType, dictType)
                .eq(DictData::getStatus, TrueOrFalse.TRUE)
                .orderByAsc(DictData::getDictSort).list();
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 获取字典数据
     *
     * @param types 字典类型集合
     */
    @Override
    public Map<String, List<DictData>> findByTypes(String[] types) {
        Map<String, List<DictData>> map = new HashMap<>();
        String[] typeList = Convert.toStrArray(types);
        for (String type : typeList) {
            map.put(type, selectDictDataByType(type));
        }
        return map;
    }

    /**
     * 获取字典数据
     *
     * @param types 字典类型集合
     */
    @Override
    public Map<String, List<Dict>> findDictByTypes(String[] types) {
        Map<String, List<Dict>> map = new HashMap<>();
        String[] typeList = Convert.toStrArray(types);
        for (String type : typeList) {
            List<Dict> dicts = selectDictDataByType(type).stream().map(x -> {
                Dict dict = new Dict();
                BeanUtils.copyProperties(x, dict);
                return dict;
            }).collect(Collectors.toList());
            map.put(type, dicts);
        }
        return map;
    }
}
