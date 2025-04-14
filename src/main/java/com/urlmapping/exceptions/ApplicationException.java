package com.urlmapping.exceptions;

public abstract class ApplicationException extends RuntimeException {

    private final String code;
    private final String description;

    protected ApplicationException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
