package com.shortener.infra;

import com.shortener.domain.entities.Shortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Protocol;

import java.util.Optional;


/**
 * Created by isvaldo on 26/03/16.
 */
@Configuration
public class DataBaseConfiguration {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public StringRedisSerializer stringRedisSerializer(){
        return new StringRedisSerializer();
    }


    @Bean
    public JacksonJsonRedisSerializer<Object> jacksonJsonRedisSerializer(){
        return new JacksonJsonRedisSerializer<>(Object.class);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setUsePool(true);

        // pc 32 bits, n tenho docker (:
        // so em produção
        String host = Optional.ofNullable(System.getenv("REDIS_PORT_6379_TCP_ADDR"))
                .orElse("127.1.1.0");

        jedisConnFactory.setHostName(host);
            jedisConnFactory.setPort(6379);
        jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
        jedisConnFactory.setPassword("");
        return jedisConnFactory;
    }


}
