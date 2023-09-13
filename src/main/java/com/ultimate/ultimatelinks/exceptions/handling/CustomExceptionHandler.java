package com.ultimate.ultimatelinks.exceptions.handling;

import com.ultimate.ultimatelinks.exceptions.linkEx.LinkAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ResponseError handleOther (Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.I_AM_A_TEAPOT, "Случилась непредвиденная ошибка!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleUserExistException(UserAlreadyExistException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.BAD_REQUEST, "Пользователь уже существует!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUserNotExistException(UserIsNotExistException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.NOT_FOUND, "Пользователь не существует!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleLinkExistException(LinkAlreadyExistException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.BAD_REQUEST, "Ссылка уже существует!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleLinkExistException(LinkIsNotExistException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.NOT_FOUND, "Ссылка не существует!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUserNotFoundException(UsernameNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(HttpStatus.NOT_FOUND, "ТАКОЙ пользователь уже существует!");
    }

}
