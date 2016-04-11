package com.shortener.application.controller;

import com.shortener.Application;
import com.shortener.domain.entities.Shortener;
import com.shortener.domain.repository.ShortenerRepository;
import com.shortener.domain.response.ErrorResponse;
import com.shortener.domain.services.ShortenerBuilder;
import com.shortener.domain.response.ShortenerResponse;
import com.shortener.infra.StatusError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by isvaldo on 26/03/16.
 */

@RestController
public class ShortenerApiController {

    @Autowired
    private ShortenerBuilder shortenerBuilder;

    @Autowired
    private ShortenerRepository shortenerRepository;



    @RequestMapping(value = "/api/create", method = RequestMethod.GET)
    public ResponseEntity<?> create(@RequestParam String url,
                                    @RequestParam(value="customName", required = false) String customName) {

        final Long startTime = System.currentTimeMillis();
        try {
            shortenerBuilder.
                    withTargetUrl(url).
                    withCustomName(customName).
                    save();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(shortenerBuilder.getId(),
                            StatusError.ERROR_101, StatusError.ERROR_101_DESCRIPTION));
        }

        final Long endTime = System.currentTimeMillis();


        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ShortenerResponse(shortenerBuilder.getId(), Application.SHORTENER_DOMAIN+shortenerBuilder.getId(),
                        new HashMap<String, String>() {{
                        put("time", String.valueOf(endTime - startTime)+"ms");
                        }}
                ));

    }


    @RequestMapping("/api/info")
    @ResponseBody
    public List<Shortener> info(){

        final List<Shortener> shorteners = shortenerRepository.findAll();
        Collections.sort(shorteners, (Shortener s1, Shortener s2) -> s2.views.compareTo(s1.views));
        return shorteners;
    }

}

