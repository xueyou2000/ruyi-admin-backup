package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.Oss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 文件查询
 *
 * @author xueyou
 * @date 2020-12-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OssQuery", description = "文件查询")
public class OssQuery extends BaseQueryDto implements Serializable {

    /**
     * 文件
     */
    @ApiModelProperty(value = "文件")
    private Oss oss;

}