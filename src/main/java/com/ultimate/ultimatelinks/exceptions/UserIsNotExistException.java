package com.ultimate.ultimatelinks.exceptions;

public class UserIsNotExistException extends RuntimeException {
    public UserIsNotExistException(String message) {
        super(message);
    }
}
