package com.xueyou.admin.system.model.dto;

import com.xueyou.admin.common.core.dto.BaseQueryDto;
import com.xueyou.admin.system.domain.LoginInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 登陆日志查询
 *
 * @author xueyou
 * @date 2020/12/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "LoginInfoQuery", description = "登陆日志查询")
public class LoginInfoQuery extends BaseQueryDto implements Serializable {

    /**
     * 登陆信息
     */
    @ApiModelProperty(value = "登陆信息")
    private LoginInfo loginInfo = new LoginInfo();

}
