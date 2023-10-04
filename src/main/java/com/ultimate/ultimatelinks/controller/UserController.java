package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.LinkClicksDto;
import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.service.LinkService;
import com.ultimate.ultimatelinks.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Validated
public class UserController {

    private final UserService userService;
    private final LinkService linkService;

    @PutMapping("/registration")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @GetMapping("/user/info/{id}")
    public ResponseEntity<ReturnedUserDto> getUser(
            @PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.status(200).body(userService.getUserInfo(id));
    }

    @DeleteMapping("/user/{userID}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("userID") @Min(1) Long userID) {
        userService.deleteUser(userID);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/user/link/{linkID}")
    public ResponseEntity<LinkClicksDto> getLinkInfo(
            @PathVariable("linkID") @Min(1) Long linkID) {
        return ResponseEntity.status(200).body(linkService.getLinkInfo(linkID));
    }

    @GetMapping("/user/link/all/{userID}")
    public ResponseEntity<Object> getUsersLink(
            @PathVariable("userID") @Min(1) Long userID) {
        return ResponseEntity.status(200).body(linkService.getAllLinks(userID));
    }

}
