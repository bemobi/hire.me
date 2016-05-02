package com.shortener.application.controller;

import com.shortener.domain.entities.Shortener;
import com.shortener.domain.response.ErrorResponse;
import com.shortener.domain.repository.ShortenerRepository;
import com.shortener.infra.StatusError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by isvaldo on 03/04/16.
 */

@Controller
public class ShortenerDispatcherController {

    @Autowired
    private ShortenerRepository shortenerRepository;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> index(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        final Shortener shortener = shortenerRepository.findById(id);

        if (shortener == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(id,
                    StatusError.ERROR_102,
                    StatusError.ERROR_102_DESCRIPTION));
        }


        /*
        * shortenerRepository.update(id, "views", 1L)
        * Redis tem o HINCRBY, mas não consegui implementar aqui
        * WRONGTYPE Operation against a key holding
        * Estou errando algum conceito no redis.
        *
        * TODO: tentar essa solução: com RedisAtomicLong
        * https://github.com/spring-projects/spring-data-keyvalue-examples/blob/master/retwisj/src/main/java/org/springframework/data/redis/samples/retwisj/redis/RetwisRepository.java
        * */
        //shortener.setViews(shortener.getViews() + 1);
        //shortenerRepository.save(shortener);
        response.sendRedirect(shortener.getTargetUrl());
        return ResponseEntity.accepted().body(shortener);
    }
}
