package com.ultimate.ultimatelinks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkClicksDto {

    private Long id;

    private String sourceLink;

    private String shortLink;

    private Long clicksCount;

}
