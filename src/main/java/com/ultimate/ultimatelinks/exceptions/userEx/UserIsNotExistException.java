package com.ultimate.ultimatelinks.exceptions.userEx;

public class UserIsNotExistException extends RuntimeException {
    public UserIsNotExistException(String message) {
        super(message);
    }
}
