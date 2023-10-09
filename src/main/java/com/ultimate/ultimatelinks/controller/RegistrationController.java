package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Validated
@Tag(name = "Registration Controller", description = "Контроллер для регистрации")
public class RegistrationController {

    private final UserService userService;

    @PutMapping("/registration")
    @Operation(summary = "Создание пользователя - [возвращает созданного пользователя]")
    public ResponseEntity<ReturnedUserDto> createUser(@Valid @RequestBody UserDto user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }
}
