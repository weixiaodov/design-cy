package com.design.cy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @author chenyun
 * @date 2022/6/7 14:52
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);

        // JSON序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        if (redisProperties.getCluster() != null) {
//            //集群模式配置
//            List<String> nodes = redisProperties.getCluster().getNodes();
//            List<String> clusterNodes = new ArrayList<>();
//            for (int i = 0; i < nodes.size(); i++) {
//                clusterNodes.add("redis://" + nodes.get(i));
//            }
//            ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                    .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//
//            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
//                clusterServersConfig.setPassword(redisProperties.getPassword());
//            }
//            config.setCheckLockSyncedSlaves(false);
//        } else {
//            //单节点配置
//            SingleServerConfig singleServer = config.useSingleServer();
//            String redisUrl = String.format("redis://%s:%s", redisProperties.getHost() + "", redisProperties.getPort() + "");
//            singleServer.setAddress(redisUrl);
//            singleServer.setDatabase(redisProperties.getDatabase());
//            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
//                singleServer.setPassword(redisProperties.getPassword());
//            }
//        }
//        return Redisson.create(config);
//    }

}
