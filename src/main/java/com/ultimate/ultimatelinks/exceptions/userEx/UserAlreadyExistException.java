package com.ultimate.ultimatelinks.exceptions.userEx;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
