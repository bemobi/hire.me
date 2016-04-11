package com.shortener.domain.repository;

import com.shortener.domain.entities.Shortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by isvaldo on 26/03/16.
 */
@Repository
public class ShortenerRepository {

    @Autowired
    private RedisTemplate<String, Shortener> redisTemplate;

    public void save(final Shortener shortener){
        redisTemplate.opsForValue().set(shortener.getId(), shortener);
    }


    public Shortener findById(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List<Shortener> findAll(){

        return redisTemplate.keys("*").stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public Integer countAll(){
        return redisTemplate.keys("*").size();
    }


    public void deleteByKey(String key){
        redisTemplate.delete(key);
    }



}
