package com.xueyou.admin.common.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * redis的配置定义
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 11:55 上午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class MyRedisConfig {

    /**
     * 主机名
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库索引
     */
    private int dbIndex;

    /**
     * 连接超时时间
     */
    private int connTimeout;

    /**
     * JedisPool配置
     */
    private RedisPool pool;

    /**
     * JedisPool配置
     */
    @Data
    static class RedisPool {

        /**
         * 资源池中最大连接数
         * 最多分配多少个redis实例, -1=无限制
         */
        private int maxTotal;

        /**
         * 资源池允许最小空闲的连接数
         *
         */
        private int minIdle;

        /**
         * 资源池允许最大空闲的连接数
         *
         */
        private int maxIdle;

        /**
         * 申请redis实例时, 超过maxWaitMillis时间, 会报JedisConnectionException
         *
         */
        private long maxWaitMillis;

        /**
         * 资源池用尽后，调用者是否要等待
         * 只有当为true时, maxWaitMillis才会生效 默认值true
         */
        private boolean blockWhenExhausted;

        /**
         * 向资源池借用连接时是否做连接有效性检测(ping)
         * 无效连接会被移除 默认值 false 业务量很大时候建议设置为false(多一次ping的开销)
         */
        private boolean testOnBorrow;

        /**
         * 资源池归还连接时是否做连接有效性检测(ping)
         * 无效连接会被移除 默认值 false 业务量很大时候建议设置为false(多一次ping的开销)
         */
        private boolean testOnReturn;

        /**
         * 空闲Jedis对象检测
         * 下面四个参数组合来完成，testWhileIdle是该功能的开关
         */
        private boolean testWhileIdle;

        /**
         * 资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除
         * 默认值1000*60 *30 = 30分钟
         */
        private long minEvictableIdleTimeMillis;

        /**
         * 空闲资源的检测周期(单位为毫秒), 默认值 -1, 建议设置30000
         */
        private long timeBetweenEvictionRunsMillis;

        /**
         * 做空闲资源检测时，每次的采样数， 默认值3
         */
        private int numTestsPerEvictionRun;

        /**
         * 如果要连接池只根据softMinEvictableIdleTimeMillis进程逐出，那么需要将minEvictableIdleTimeMillis设置为负数（即最大值）
         * 如果要连接池只根据minEvictableIdleTimeMillis进程逐出，那么需要将softMinEvictableIdleTimeMillis设置为负数（即最大值）
         */
        private long softMinEvictableIdleTimeMillis;

        private boolean lifo;

    }

}
