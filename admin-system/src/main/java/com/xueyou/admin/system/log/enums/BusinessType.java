package com.xueyou.admin.system.log.enums;

/**
 * 业务操作类型
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/13 2:01 下午
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空
     */
    CLEAN,
}
