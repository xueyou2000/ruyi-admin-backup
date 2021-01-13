package com.xueyou.admin.common.core.constant;

/**
 * 任务调度通用常量
 *
 * @author xueyou
 * @date 2020/12/24
 */
public class ScheduleConstants {

    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";

    /**
     * 正常
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 暂停
     */
    public static final String STATUS_PAUSE = "1";


}
