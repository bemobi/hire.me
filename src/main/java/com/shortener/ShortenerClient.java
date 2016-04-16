package com.shortener;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.shortener.domain.entities.Shortener;

/**
 * Created by isvaldo on 11/04/16.
 */
public class ShortenerClient {

    private String url;
    private String customName;

    ShortenerClient(){

    }

    ShortenerClient(String url) throws UnirestException {
        this.url = url;
        create();
    }

    ShortenerClient(String url, String customName) throws UnirestException {
        this.url = url;
        this.customName =customName;
        create();
    }


    public ShortenerClient withUrl(String url){
        return this;
    }

    public ShortenerClient withCustomName(String customName){
        return this;
    }

    public String create() throws UnirestException {
        return Unirest.get(Application.SHORTENER_DOMAIN+"api/create?url={url}&customName={name}")
                .routeParam("url", this.url)
                .routeParam("name", this.customName)
                .asJson().getBody().toString();
    }
}
