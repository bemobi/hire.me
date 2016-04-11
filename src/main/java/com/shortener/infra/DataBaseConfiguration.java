package com.shortener.infra;

import com.shortener.domain.entities.Shortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Protocol;


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
    public JacksonJsonRedisSerializer<Shortener> jacksonJsonRedisSerializer(){
        return new JacksonJsonRedisSerializer<Shortener>(Shortener.class);
    }

    @Bean
    public RedisTemplate<String, Shortener> redisTemplate() {
        RedisTemplate<String, Shortener> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setUsePool(true);
        jedisConnFactory.setHostName(System.getenv("REDIS_PORT_6379_TCP_ADDR"));
        jedisConnFactory.setPort(6379);
        jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
        jedisConnFactory.setPassword("");
        return jedisConnFactory;
    }


}
