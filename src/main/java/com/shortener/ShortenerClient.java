package com.shortener;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by isvaldo on 11/04/16.
 */
public class ShortenerClient {

    private String url;
    private String name;

    ShortenerClient(){

    }

    ShortenerClient(String url) throws UnirestException {
        this.url = url;
        create();
    }

    ShortenerClient(String url, String customName) throws UnirestException {
        this.url = url;
        this.name =customName;
        create();
    }


    public ShortenerClient withUrl(String url){
        this.url = url;
        return this;
    }

    public ShortenerClient withCustomName(String customName){
        this.name = customName;
        return this;
    }

    public String create() throws UnirestException {
        return Unirest.post(Application.SHORTENER_DOMAIN)
                .field("url", this.url)
                .field("name", this.name)
                .asJson().getBody().toString();
    }

    public String update() throws UnirestException {
        return Unirest.put(Application.SHORTENER_DOMAIN)
                .field("url", this.url)
                .field("name", this.name)
                .asJson().getBody().toString();
    }

    public String delete() throws UnirestException {
        return Unirest.put(Application.SHORTENER_DOMAIN)
                .field("name", this.name)
                .asJson().getBody().toString();
    }

    public String get() throws UnirestException {
        return Unirest.get(Application.SHORTENER_DOMAIN+"shortener/<name>")
                .routeParam("name", this.name)
                .asJson().getBody().toString();
    }



}
