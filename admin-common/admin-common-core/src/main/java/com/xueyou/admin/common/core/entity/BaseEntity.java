package com.xueyou.admin.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体基础类
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/30 12:44 下午
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 请求参数
     * 用于sql参数注入, 比如数据权限的拦截注入
     */
    @Transient
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

}
