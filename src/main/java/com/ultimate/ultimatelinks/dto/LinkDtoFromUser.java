package com.ultimate.ultimatelinks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LinkDtoFromUser {
    private Long userID;
    private String sourceLink;
}
