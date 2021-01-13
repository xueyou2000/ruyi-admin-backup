package com.xueyou.admin.common.core.entity;

import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;

/**
 * @author xueyou
 * @date 2020/12/23
 */
@Data
public class Dict {

    /**
     * 字典编码
     */
    private Long dictCode;

    /**
     * 字典排序
     */
    private Long dictSort;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 样式属性
     */
    private String cssClass;

    /**
     * 是否默认
     */
    private TrueOrFalse isDefault;

    /**
     * 状态
     */
    private TrueOrFalse status;

    /**
     * 备注
     */
    private String remark;

}
