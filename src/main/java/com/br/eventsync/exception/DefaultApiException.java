package com.br.eventsync.exception;

public class DefaultApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DefaultApiException(String msg) {
        super(msg);
    }
}