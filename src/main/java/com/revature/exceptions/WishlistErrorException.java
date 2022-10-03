package com.revature.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WishlistErrorException extends RuntimeException {
    public WishlistErrorException(String message) {
        super(message);
    }
}
