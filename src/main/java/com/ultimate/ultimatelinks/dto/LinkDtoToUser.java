package com.ultimate.ultimatelinks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LinkDtoToUser {
    private Long id;
    private String sourceLink;
    private String shortLink;
}
