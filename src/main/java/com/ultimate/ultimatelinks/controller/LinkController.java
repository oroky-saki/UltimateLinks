package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.service.LinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
@RestControllerAdvice
@Validated
public class LinkController {

    private final LinkService linkService;

    @PutMapping("/put")
    public ResponseEntity<LinkDtoToUser> createShortLink (@Valid @RequestBody LinkDtoFromUser linkDtoFromUser) {
        return ResponseEntity.status(201).body(linkService.createLink(linkDtoFromUser));
    }

    @DeleteMapping("/{linkID}")
    public ResponseEntity<String> deleteLink(
            @PathVariable("linkID") @Min(1) Long linkID) {
        linkService.deleteLink(linkID);
        return ResponseEntity.status(200).build();
    }

}
