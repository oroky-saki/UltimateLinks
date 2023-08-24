package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@RestControllerAdvice
public class UserController {

    private final UserService userService;

    @PutMapping("/registration")
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getUserInfo(id));
    }

}
