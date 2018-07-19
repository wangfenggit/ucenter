package com.ucenter.api.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


@Configuration
@PropertySource({"classpath:application-redis.properties"})
public class RedisConfig {

    @Value("${redis.config.maxTotal}")
    private int maxTotal;
    @Value("${redis.config.minIdle}")
    private int minIdle;
    @Value("${redis.config.maxWaitMillis}")
    private long maxWaitMillis;
    @Value("${redis.cluster.addr}")
    private String ipAndPorts;


    @Bean
    public RedisClient getJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        if (StringUtils.isBlank(ipAndPorts)) {
            return null;
        }
        String[] hostPortArr = ipAndPorts.split(",");

        JedisPoolConfig redisConfig = new JedisPoolConfig();

        redisConfig.setBlockWhenExhausted(true);
        redisConfig.setLifo(false);
        redisConfig.setTestOnBorrow(false);
        redisConfig.setTestOnReturn(false);
        redisConfig.setTestWhileIdle(true);
        redisConfig.setNumTestsPerEvictionRun(-2);
        redisConfig.setTimeBetweenEvictionRunsMillis(30000);
        redisConfig.setSoftMinEvictableIdleTimeMillis(3600000);
        redisConfig.setMinEvictableIdleTimeMillis(-1);

        for (String hostAndPort : hostPortArr) {
            String[] arr = hostAndPort.split(":");
            // Jedis Cluster will attempt to discover cluster nodes automatically
            jedisClusterNodes.add(new HostAndPort(arr[0], Integer.valueOf(arr[1])));
        }

        return new RedisClient(jedisClusterNodes, redisConfig);
    }

}
