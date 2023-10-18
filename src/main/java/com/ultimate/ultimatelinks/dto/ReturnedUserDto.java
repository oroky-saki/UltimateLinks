package com.ultimate.ultimatelinks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReturnedUserDto {

    @Min(1)
    private Long id;

    @Email
    private String email;

    @NotBlank
    private String name;

    public ReturnedUserDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
