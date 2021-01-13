package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型
 * @author xueyou
 * @date 2020/12/21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_type")
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态
     */
    private TrueOrFalse status;

    /**
     * 备注
     */
    private String remark;

}
