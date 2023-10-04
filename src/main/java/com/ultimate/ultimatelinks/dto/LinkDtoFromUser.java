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
public class LinkDtoFromUser {

    @Min(1)
    private Long userID;

    @NotBlank()
    @Size(min = 8)
    private String sourceLink;
}
