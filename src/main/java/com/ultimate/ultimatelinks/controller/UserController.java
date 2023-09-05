package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.LinkClicksDto;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.service.LinkService;
import com.ultimate.ultimatelinks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@RestControllerAdvice
public class UserController {

    private final UserService userService;
    private final LinkService linkService;

    @PutMapping("/registration")
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.getUserInfo(id));
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/link/{linkID}")
    public ResponseEntity<LinkClicksDto> getLinkInfo(@PathVariable Long linkID) {
        return ResponseEntity.status(200).body(linkService.getLinkInfo(linkID));
    }

    @GetMapping("/link/all/{userID}")
    public ResponseEntity<Object> getUsersLink(@PathVariable Long userID) {
        return ResponseEntity.status(200).body(linkService.getAllLinks(userID));
    }

}
