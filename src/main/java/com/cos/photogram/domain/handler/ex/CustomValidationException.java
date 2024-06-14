package com.cos.photogram.domain.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;
    private Map<String, String> details;

    public CustomValidationException(String msg) {
        this.msg = msg;
    }

    public CustomValidationException(String msg, Map<String, String> details) {
        this.msg = msg;
        this.details = details;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, String> getDetails() {
        return details;
    }

}
