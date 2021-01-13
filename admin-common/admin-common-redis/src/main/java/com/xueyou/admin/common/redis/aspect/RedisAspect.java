package com.xueyou.admin.common.redis.aspect;

import com.xueyou.admin.common.redis.annotation.RedisCache;
import com.xueyou.admin.common.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Redis缓存切面
 *
 * @author xueyou
 * @version V1.0.0
 * @since 2020/10/12 2:57 下午
 */
@Slf4j
@Component
@Aspect
public class RedisAspect {

    /**
     * 定义切入点，使用了 @RedisCache 的方法
     */
    @Pointcut("@annotation(com.xueyou.admin.common.redis.annotation.RedisCache)")
    public void redisCachePoint() {
    }

    /**
     * 环绕通知，方法拦截器
     */
    @Around("redisCachePoint()")
    public Object WriteReadFromRedis(ProceedingJoinPoint point) {
        try {
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            // 获取RedisCache注解
            RedisCache redisCache = method.getAnnotation(RedisCache.class);
            Class<?> returnType = ((MethodSignature) point.getSignature()).getReturnType();
            if (redisCache != null) {
                // 查询操作
                log.debug("<====== method:{} 进入 redisCache 切面 ======>", method.getName());
                String fieldKey = parseKey(redisCache.fieldKey(), method, point.getArgs());
                String rk = redisCache.key() + ":" + fieldKey;
                Object obj = RedisUtils.get(rk, returnType);
                if (obj == null) {
                    // Redis 中不存在，则从数据库中查找，并保存到 Redis
                    log.debug("<====== Redis 中不存在该记录，从数据库查找 ======>");
                    obj = point.proceed();
                    if (obj != null) {
                        if (redisCache.expired() > 0) {
                            RedisUtils.setEx(rk, obj, (int) redisCache.expired());
                        } else {
                            RedisUtils.set(rk, obj);
                        }
                    }
                }
                return obj;
            }
        } catch (Throwable ex) {
            log.error("<====== RedisCache 执行异常:  ======>", ex);
        }
        return null;
    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @param key       建
     * @param method    方法
     * @param args      参数
     */
    private String parseKey(String key, Method method, Object[] args) {
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < Objects.requireNonNull(paraNameArr).length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}
