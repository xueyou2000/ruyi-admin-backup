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
     * 字典国际化标签
     * 备用字段，默认 字典类型.字典值 作为国际化key，不满足需自定义时才使用
     */
    private String dictLangLabel;

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
