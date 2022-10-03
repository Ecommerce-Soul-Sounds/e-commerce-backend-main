package com.revature.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartErrorException  extends RuntimeException{
    public CartErrorException() {
    }

    public CartErrorException(String message) {
        super(message);
    }

    public CartErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartErrorException(Throwable cause) {
        super(cause);
    }

    public CartErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
