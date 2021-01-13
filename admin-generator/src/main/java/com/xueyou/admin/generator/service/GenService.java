package com.xueyou.admin.generator.service;

import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.generator.model.TableInfo;

import java.util.List;

public interface GenService {

    /**
     * 生成代码
     *
     * @param tableName 表名
     */
    byte[] generatorCode(String tableName);


    List<TableInfo> queryTable();

}
