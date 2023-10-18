package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.service.LinkService;
import com.ultimate.ultimatelinks.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Validated
@Tag(name = "User Controller", description = "Контроллер для работы со пользователем")
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final LinkService linkService;

    @GetMapping("/info")
    @Operation(summary = "Получение информации о текущем авторизованном пользователе - [возвращает пользователя]")
    public ResponseEntity<ReturnedUserDto> getAuthUser() {
        return ResponseEntity.status(200).body(userService.getAuthUserInfo());
    }

    @DeleteMapping("/{userID}")
    @Operation(summary = "Удаление пользователя по userID - [возвращает пустое тело и код 200, удаляет все ссылки пользователя]")
    public ResponseEntity<String> deleteUser(
            @PathVariable("userID") @Min(1) Long userID) {
        userService.deleteUser(userID);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/link/all/{userID}")
    @Operation(summary = "Получение информации о всех ссылках пользователя по userID- [возвращает список ссылок]")
    public ResponseEntity<List<LinkDtoToUser>> getUsersLink(
            @PathVariable("userID") @Min(1) Long userID) {
        return ResponseEntity.status(200).body(linkService.getAllLinks(userID));
    }

}