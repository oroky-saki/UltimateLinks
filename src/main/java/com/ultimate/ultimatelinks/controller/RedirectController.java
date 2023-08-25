package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{shortLink}")
    public RedirectView redirect(@PathVariable String shortLink) {
        return new RedirectView(linkService.findShortLink(shortLink));
    }
}
