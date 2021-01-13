package com.xueyou.admin.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyou.admin.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 文件
 *
 * @author xueyou
 * @date 2020-12-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_oss")
public class Oss extends BaseEntity {

    private static final long serialVersionUID = 1L;

        
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
            
    /**
     * 文件名
     */
    private String fileName;
            
    /**
     * 文件后缀名
     */
    private String fileSuffix;
            
    /**
     * URL地址
     */
    private String url;
            
    /**
     * 云存储服务商
     */
    private Long service;
                                        
}