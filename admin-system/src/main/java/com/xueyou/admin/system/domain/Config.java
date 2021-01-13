package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import com.xueyou.admin.common.core.enums.TrueOrFalse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统参数配置
 *
 * @author xueyou
 * @date 2020/12/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class Config extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    @TableId(type = IdType.AUTO)
    private Long configId;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置
     */
    private TrueOrFalse configType;

    /**
     * 备注
     */
    private String remark;


}
