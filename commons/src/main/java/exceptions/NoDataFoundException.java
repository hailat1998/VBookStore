package com.hd.vbookstore.commons.exceptions;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}