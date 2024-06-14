package com.cos.photogram.domain.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;
    public CustomApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

}
