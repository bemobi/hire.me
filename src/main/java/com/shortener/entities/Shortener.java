package com.shortener.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * Created by isvaldo on 26/03/16.
 */
public class Shortener implements Serializable{
    private static final long serialVersionUID = 1L;
    private static long longId = 100;

    public String id;
    public String targetUrl;

    @NotEmpty
    public Long views;

    public Shortener() {}

    public Shortener(String id, String targetUrl) {
        this.id = id;
        this.views = 0L;
        this.targetUrl = targetUrl;
    }

    public static String getNextId(){
        return String.valueOf(longId)+1;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static long getLongId() {
        return longId;
    }

    public static void setLongId(long longId) {
        Shortener.longId = longId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
