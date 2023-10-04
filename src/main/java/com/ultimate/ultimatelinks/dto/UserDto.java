package com.ultimate.ultimatelinks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
}
