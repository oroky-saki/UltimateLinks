package com.ultimate.ultimatelinks.controller;

import com.ultimate.ultimatelinks.dto.ClickStatsDto;
import com.ultimate.ultimatelinks.dto.LinkClicksDto;
import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.service.LinkClickService;
import com.ultimate.ultimatelinks.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/link")
@RequiredArgsConstructor
@RestControllerAdvice
@Validated
@Tag(name = "Link Controller", description = "Контроллер для работы со ссылками")
public class LinkController {

    private final LinkService linkService;
    private final LinkClickService clickService;

    @PutMapping("/put")
    @Operation(summary = "Создание короткой ссылки - [возвращает созданную короткую ссылку]")
    public ResponseEntity<LinkDtoToUser> createShortLink (@Valid @RequestBody LinkDtoFromUser linkDtoFromUser) {
        return ResponseEntity.status(201).body(linkService.createLink(linkDtoFromUser));
    }

    @DeleteMapping("/{linkID}")
    @Operation(summary = "Удаление короткой ссылки по ID - [возвращает пустое тело и код 200, удаляет все переходы по этой ссылке]")
    public ResponseEntity<String> deleteLink(
            @PathVariable("linkID") @Min(1) Long linkID) {
        linkService.deleteLink(linkID);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{linkID}")
    @Operation(summary = "Получение информации о ссылке по linkID - [возвращает информацию о ссылке]")
    public ResponseEntity<LinkClicksDto> getLinkInfo(
            @PathVariable("linkID") @Min(1) Long linkID) {
        return ResponseEntity.status(200).body(linkService.getLinkInfo(linkID));
    }

    @GetMapping("/days/{linkID}")
    @Operation(summary = "Получение количества кликов по ссылке по ID ПО ДНЯМ- [возвращает массив объектов с датой(dd-mm-yyyy, например '04-10-2023') и количеством кликов]")
    public ResponseEntity<List<ClickStatsDto>> getLinkClicksByDays(
            @PathVariable("linkID") @Min(1) Long linkID) {
        return ResponseEntity.status(200).body(clickService.getClicksCountByDays(linkID));
    }

    @GetMapping("/hours/{linkID}")
    @Operation(summary = "Получение количества кликов по ссылке по ID ПО ЧАСАМ - [возвращает массив объектов с датой(dd-mm-yyyy, например '04-10-2023') и часами и количеством кликов]")
    public ResponseEntity<List<ClickStatsDto>> getLinkClicksByHours(
            @PathVariable("linkID") @Min(1) Long linkID, @RequestParam String date) {
        return ResponseEntity.status(200).body(clickService.getClicksCountByHours(linkID, date));
    }

    @GetMapping("/minutes/{linkID}")
    @Operation(summary = "Получение количества кликов по ссылке по ID ПО МИНУТАМ - [возвращает массив объектов с датой(dd-mm-yyyy, например '04-10-2023'), часами и минутами и количеством кликов]")
    public ResponseEntity<List<ClickStatsDto>> getLinkClicksByMinutes(
            @PathVariable("linkID") @Min(1) Long linkID, @RequestParam String date) {
        return ResponseEntity.status(200).body(clickService.getClicksCountByMinutes(linkID, date));
    }

    @GetMapping("/top")
    @Operation(summary = "Получение 20 самых популярных (по переходам) ссылок - [возвращает массив объектов с названием ссылки количеством кликов]")
    public ResponseEntity<List<ClickStatsDto>> getPopularLinks () {
        return ResponseEntity.status(200).body(clickService.getPopularLinks());
    }
}
