package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单查询
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/22 4:28 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "MenuQueryDto", description = "菜单查询")
public class MenuQueryDto extends BaseQueryDto implements Serializable {
    /**
     * 菜单信息
     */
    @ApiModelProperty(value = "菜单信息")
    private Menu menu = new Menu();

}
