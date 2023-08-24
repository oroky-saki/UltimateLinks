package com.ultimate.ultimatelinks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String name;
}
