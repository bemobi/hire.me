package com.urlmapping.exceptions;

public class ShortenedURLNotFoundException extends ApplicationException {

    private static final String CODE = "002";
    private static final String DESCRIPTION = "SHORTENED URL NOT FOUND";

    private final String customAlias;

    public ShortenedURLNotFoundException(String alias) {
        super(CODE, DESCRIPTION);
        this.customAlias = alias;
    }

    public String getCustomAlias() {
        return customAlias;
    }
}
