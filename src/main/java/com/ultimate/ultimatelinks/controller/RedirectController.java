package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.service.LinkService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@Validated
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{shortLink}")
    public RedirectView redirect(
            @PathVariable("shortLink") @NotBlank @Size(min = 7, max = 7) String shortLink) {
        return new RedirectView(linkService.findShortLink(shortLink));
    }
}
