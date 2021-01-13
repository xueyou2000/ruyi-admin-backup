package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.DictData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 字典数据查询
 *
 * @author xueyou
 * @date 2020/12/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "DictDataQuery", description = "字典数据查询")
public class DictDataQuery extends BaseQueryDto implements Serializable {

    /**
     * 字典数据
     */
    @ApiModelProperty(value = "字典数据")
    private DictData dictData;


}
