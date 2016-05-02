package com.shortener.domain.repository;

import com.shortener.domain.entities.Shortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by isvaldo on 26/03/16.
 */
@Repository
public class ShortenerRepository {

    public static String URL_PREFIX = "url_";
    public static String VIEWS_PREFIX = "views_";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(final Shortener shortener){
        redisTemplate.opsForValue().set(URL_PREFIX+shortener.getId(), shortener.getTargetUrl());
        redisTemplate.opsForValue().set(VIEWS_PREFIX+shortener.getId(), shortener.getViews());
    }


    public Shortener findById(final String key) {

        final Object  url =  redisTemplate.opsForValue().get(URL_PREFIX+key);
        final Object views = redisTemplate.opsForValue().get(VIEWS_PREFIX+key);

        return (Objects.nonNull(url) && Objects.nonNull(views))?
                new Shortener(key, url.toString(), Long.parseLong(views.toString())) : null;
    }

    public List<Shortener> findAll(){
        final Set<String> keys = redisTemplate.keys(URL_PREFIX + "*");
        List<Shortener> shorteners = new ArrayList<>();
        for (String key: keys) {
            shorteners.add(findById(key.replace(URL_PREFIX,"")));
        }
        return shorteners;
    }

    public Integer countAll(){
        return redisTemplate.keys(URL_PREFIX+"*").size();
    }

    public void deleteByKey(String key){
        redisTemplate.delete(URL_PREFIX+key);
        redisTemplate.delete(VIEWS_PREFIX+key);
    }

    public void update(String key, Long value){
        redisTemplate.opsForValue().increment(VIEWS_PREFIX+key, value);
    }



}
