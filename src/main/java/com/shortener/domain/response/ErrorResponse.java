package com.shortener.domain.response;

/**
 * Created by isvaldo on 03/04/16.
 */
public class ErrorResponse {
    public String alias;
    public String err_code;
    public String description;

    public ErrorResponse(String alias, String err_code, String description) {
        this.alias = alias;
        this.err_code = err_code;
        this.description = description;
    }
}
