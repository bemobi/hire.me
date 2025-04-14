package com.urlmapping.exceptions;

public class URLNotProvidedException extends ApplicationException {

    private static final String CODE = "003";
    private static final String DESCRIPTION = "URL NOT PROVIDED.";

    private final String customAlias;

    public URLNotProvidedException(String customAlias) {
        super(CODE, DESCRIPTION);
        this.customAlias = customAlias;
    }

    public String getCustomAlias() {
        return customAlias;
    }


}
