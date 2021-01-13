package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.DictType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 字典类型查询
 *
 * @author xueyou
 * @date 2020/12/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "DictTypeQuery", description = "字典类型查询")
public class DictTypeQuery extends BaseQueryDto implements Serializable {

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型")
    private DictType dictType = new DictType();

}
