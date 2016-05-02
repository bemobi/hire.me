package com.shortener.application.controller;

import com.shortener.Application;
import com.shortener.domain.entities.Shortener;
import com.shortener.domain.repository.ShortenerRepository;
import com.shortener.domain.response.ErrorResponse;
import com.shortener.domain.response.ShortenerResponse;
import com.shortener.domain.services.ShortenerBuilder;
import com.shortener.infra.StatusError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
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



    @RequestMapping(value = "/shortener", method = RequestMethod.POST)
    public ResponseEntity<?> shortenerInsert(@RequestParam String url,
                                    @RequestParam(value="customName", required = false) String customName) {
        final Long startTime = System.currentTimeMillis();

        try {
            new URL(url);
            shortenerBuilder.
                    withTargetUrl(url).
                    withCustomName(customName).
                    save();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(shortenerBuilder.getId(),
                            StatusError.ERROR_101, StatusError.ERROR_101_DESCRIPTION));

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(customName,
                            StatusError.ERROR_103, StatusError.ERROR_103_DESCRIPTION));
        }

        final Long endTime = System.currentTimeMillis();


        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ShortenerResponse(shortenerBuilder.getId(), Application.SHORTENER_DOMAIN+shortenerBuilder.getId(),
                        new HashMap<String, String>() {{
                        put("time", String.valueOf(endTime - startTime)+"ms");
                        }}
                ));

    }


    @RequestMapping(value = "/shortener", method = RequestMethod.PUT)
    public ResponseEntity<?> shortenerUpdate(@RequestParam String name,
                                             @RequestParam String url){
        final  Shortener shortener = shortenerRepository.findById(name);
        if (shortener != null) {
            shortener.setTargetUrl(url);
            shortenerRepository.save(shortener);
            return ResponseEntity.status(HttpStatus.OK).body("resource updated successfully");
        }else {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("",
                            StatusError.ERROR_102, StatusError.ERROR_102_DESCRIPTION));
        }
    }

    @RequestMapping(value = "/shortener", method = RequestMethod.DELETE)
    public ResponseEntity<?> shortenerDelete(@RequestParam String name) {
       final  Shortener shortener = shortenerRepository.findById(name);
        if (shortener != null) {
            shortenerRepository.deleteByKey(name);
            return ResponseEntity.status(HttpStatus.OK).body("resource deleted successfully");
        }else {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("",
                            StatusError.ERROR_102, StatusError.ERROR_102_DESCRIPTION));
        }

    }

    @RequestMapping(value = "/shortener", method = RequestMethod.GET)
    public ResponseEntity<?> shortenerGet(@RequestParam String name){
        final Shortener shortener = shortenerRepository.findById(name);
        if (shortener != null) {
            return ResponseEntity.status(HttpStatus.OK).body(shortener);
        }else {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("",
                            StatusError.ERROR_102, StatusError.ERROR_102_DESCRIPTION));
        }
    }




    @RequestMapping("/shortener/info")
    @ResponseBody
    public List<Shortener> info(){
        /*
        http://stackoverflow.com/questions/35635337/spring-redis-sort-keys
        melhor cenario seria utilizar uma busca com redis

        SortQuery<String> query = SortQueryBuilder.sort(key).noSort().get(pidKey).get(pid + uid).get(pid + content).get(
				pid + replyPid).get(pid + replyUid).get(pid + time).limit(range.begin, range.end).build();
	    Ainda estou buscando uma solução para isso, todas as tentativas com SortQuery retornam um conjunto vazio ):

         */

        final List<Shortener> shorteners = shortenerRepository.findAll();
        Collections.sort(shorteners, (Shortener s1, Shortener s2) -> s2.views.compareTo(s1.views));
        return shorteners;
    }

}

