package com.ultimate.ultimatelinks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ClickStatsDto {
    private String rawData;
    private Long clicks;

    public ClickStatsDto(String rawData, Long clicks) {
        this.rawData = rawData;
        this.clicks = clicks;
    }
}
