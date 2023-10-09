package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@Validated
@Tag(name = "Redirect Controller", description = "Контроллер для работы со ссылками")
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{shortLink}")
    @Operation(summary = "Перенаправление по короткой ссылке")
    public RedirectView redirect(
            @PathVariable("shortLink") @NotBlank @Size(min = 7, max = 7) String shortLink) {
        return new RedirectView(linkService.findShortLink(shortLink));
    }
}
