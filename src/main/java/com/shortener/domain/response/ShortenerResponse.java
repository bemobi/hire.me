package com.shortener.domain.response;

import java.util.Map;

/**
 * Created by isvaldo on 03/04/16.
 */
public class ShortenerResponse {
    public String alias;
    public String url;
    public Map<String, String> statistics;

    public ShortenerResponse(String alias, String url, Map<String, String> statistics) {
        this.alias = alias;
        this.url = url;
        this.statistics = statistics;
    }

}
