package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据
 * @author xueyou
 * @date 2020/12/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_data")
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(type = IdType.AUTO)
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

    // ========= 以下为临时字段

    /**
     * 国际化标签名
     */
    @TableField(exist = false)
    private String langLabel;

}
