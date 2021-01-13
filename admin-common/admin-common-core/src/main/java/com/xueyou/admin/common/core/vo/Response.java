package com.xueyou.admin.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口响应
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/25 1:48 下午
 */
@Data
@ApiModel(value = "Response", description = "接口响应")
public class Response<T> {

    /**
     * 正常响应 状态码
     */
    public static final int OK = 0;

    /**
     * 正常响应 消息
     */
    public static final String OkMessage = "success";

    /**
     * 状态码
     * 成功则响应=0
     */
    @ApiModelProperty(value = "状态码")
    private int status;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T res;

    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    /**
     * 响应成功数据
     * @param data 响应数据
     * @return 成功响应
     */
    @SuppressWarnings("unchecked")
    public static <T> Response<T> ok(T data) {
        Response res = new Response<T>();
        res.status = OK;
        res.message = OkMessage;
        res.res = data;
        return res;
    }

    /**
     * 响应成功
     */
    public static Response ok() {
        Response res = new Response();
        res.status = OK;
        res.message = OkMessage;
        return res;
    }

    /**
     * 响应失败
     *
     * @param status    错误码
     * @param message   错误消息
     */
    public static Response error(int status, String message) {
        Response res = new Response();
        res.status = status;
        res.message = message;
        return res;
    }

}
