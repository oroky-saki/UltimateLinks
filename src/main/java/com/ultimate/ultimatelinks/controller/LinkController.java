package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
@RestControllerAdvice
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/test")
    public ResponseEntity<Object> getServerInfo() {
        ;
        return ResponseEntity.ok(linkService.checkSP());
    }

}
