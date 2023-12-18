package com.example.backend.util;

import lombok.Data;


@Data
public class LoginUserDto {
    private String email;
    private String nickname;
    private Long userId;

}