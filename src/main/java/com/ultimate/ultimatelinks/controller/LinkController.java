package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
@RestControllerAdvice
public class LinkController {

    private final LinkService linkService;

    @PutMapping("/new")
    public ResponseEntity<LinkDtoToUser> createShortLink (@RequestBody LinkDtoFromUser linkDtoFromUser) {
        return ResponseEntity.status(201).body(linkService.createLink(linkDtoFromUser));
    }

    @DeleteMapping("/{linkID}")
    public ResponseEntity<String> deleteLink(@PathVariable Long linkID) {
        return ResponseEntity.status(200).body(linkService.deleteLink(linkID));
    }

}