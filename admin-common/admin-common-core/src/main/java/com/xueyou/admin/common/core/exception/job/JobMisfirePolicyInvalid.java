package com.xueyou.admin.common.core.exception.job;

/**
 * 无效的定时任务出错执行策略
 *
 * @author xueyou
 * @date 2020/12/24
 */
public class JobMisfirePolicyInvalid extends JobException {

    private static final long serialVersionUID = 1L;

    public JobMisfirePolicyInvalid(String policy) {
        super("job.policy.invalid", new Object[]{policy});
    }

}
