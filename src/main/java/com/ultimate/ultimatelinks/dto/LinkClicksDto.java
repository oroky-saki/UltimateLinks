package com.ultimate.ultimatelinks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkClicksDto {

    @Min(1)
    private Long id;

    @NotBlank
    @Size(min = 8)
    private String sourceLink;

    @NotBlank()
    @Size(min = 7, max = 8)
    private String shortLink;

    @PositiveOrZero
    private Long clicksCount;

}
