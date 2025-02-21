package com.hd.vbookstore.commons.exceptions;

public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(String message) {
        super(message);
    }
}
