package com.xueyou.admin.system.aspect;

import com.alibaba.fastjson.JSON;
import com.xueyou.admin.common.core.utils.IpUtils;
import com.xueyou.admin.system.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器环绕日志打印
 * @author xueyou
 * @date 2021/5/11
 */
@Slf4j
@Aspect
@Component
public class LogAspectController {

    private ThreadLocal <Long> startTime = new ThreadLocal <>();

    /**
     * 定义切面，此方法不能有返回值，该方法只是一个标示，方法体不会被执行
     */
    @Pointcut("execution( * com.xueyou.admin.*.controller..*(..)) && !execution( * com.xueyou.admin.*.controller.FileController.*(..))")
    private void recordLog() {
    }

    /**
     * 环绕通知，决定真实的方法是否执行，而且必须有返回值。 同时在所拦截方法的前后执行一段逻辑。
     *
     * @param pjp 连接点
     * @return 执行方法的返回值
     * @throws Throwable 抛出异常
     */
    @Around("recordLog()")
    public Object aroundLogCalls(ProceedingJoinPoint pjp) throws Throwable {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String ip = IpUtils.getIpAddr(request);
//        // 类名
//        String className = pjp.getTarget().getClass().getName();
//        // 方法名
//        String methodName = pjp.getSignature().getName();
        // 参数列表
        Object[] args = pjp.getArgs();
        String url = request.getRequestURI();

        log.info("HTTP Request --> {} args={} user=[{ loginName: {}, userId: {}, ip: {} }]",
                url,
                args,
                UserUtils.getUserName(),
                UserUtils.getUserId(),
                ip);

        Object result = pjp.proceed();
//        log.info("HTTP Response <-- {} 耗时={} 结果={}", url, System.currentTimeMillis() - startTime.get(), JSON.toJSONString(result));
        return result;
    }

}
