package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.ClickStatsDto;
import com.ultimate.ultimatelinks.entities.LinkClickEntity;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.repository.LinkClickRepo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class LinkClickService {
    private final LinkClickRepo clickRepo;

    @Transactional
    public void createClick(LinkEntity link) {
        LinkClickEntity newClick = new LinkClickEntity(link, LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg")));
        clickRepo.save(newClick);
    }

    public List<ClickStatsDto> getClicksCountByDays(@Min(1) Long linkID) {
        return clickRepo.statsByDays(linkID);
    }

    public List<ClickStatsDto> getClicksCountByHours(@Min(1) Long linkID, @NotBlank String date) {
        return clickRepo.statsByHourOnDate(linkID, date);
    }
    public List<ClickStatsDto> getClicksCountByMinutes(@Min(1) Long linkID, @NotBlank String date) {
        return clickRepo.statsByMinuteOnDate(linkID, date);
    }
}
