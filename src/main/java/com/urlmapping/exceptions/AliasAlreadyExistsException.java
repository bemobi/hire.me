package com.urlmapping.exceptions;

public class AliasAlreadyExistsException extends ApplicationException {

    private static final String CODE = "001";
    private static final String DESCRIPTION = "CUSTOM ALIAS ALREADY EXISTS";

    private final String customAlias;

    public AliasAlreadyExistsException(String customAlias) {
        super(CODE, DESCRIPTION);
        this.customAlias = customAlias;
    }

    public String getCustomAlias() {
        return customAlias;
    }


}
