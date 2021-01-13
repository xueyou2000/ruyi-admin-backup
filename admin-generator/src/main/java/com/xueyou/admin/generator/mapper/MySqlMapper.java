package com.xueyou.admin.generator.mapper;

import com.xueyou.admin.generator.model.TableColumn;
import com.xueyou.admin.generator.model.TableInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MySqlMapper {

    /**
     * 查询表
     */
    List<TableInfo> queryTableList();

    /**
     * 获取表信息
     *
     * @param tableName 表名
     */
    TableInfo queryTableInfo(String tableName);

    /**
     * 获取列信息
     *
     * @param tableName 表名
     */
    List<TableColumn> queryTableColumns(String tableName);

}
