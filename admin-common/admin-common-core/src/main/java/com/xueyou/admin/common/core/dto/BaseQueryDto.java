package com.xueyou.admin.common.core.dto;

import com.xueyou.admin.common.core.dto.query.QueryBaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基本查询
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/28 11:45 上午
 */
@Data
public class BaseQueryDto implements Serializable {

    /**
     * 通用查询对象
     */
    @ApiModelProperty(value = "通用查询对象")
    private QueryBaseDto queryBaseDto = new QueryBaseDto();

}
