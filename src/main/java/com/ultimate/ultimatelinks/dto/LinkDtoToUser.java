package com.ultimate.ultimatelinks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LinkDtoToUser {

    @Min(1)
    private Long id;

    @NotBlank
    @Size(min = 8)
    private String sourceLink;

    @NotBlank
    @Size(min = 7, max = 7)
    private String shortLink;

    @NotBlank
    private String site;

    public LinkDtoToUser(Long id, String sourceLink, String shortLink, String site) {
        this.id = id;
        this.sourceLink = sourceLink;
        this.shortLink = shortLink;
        this.site = site;
    }
}
