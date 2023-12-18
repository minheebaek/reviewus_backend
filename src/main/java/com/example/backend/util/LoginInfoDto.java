package com.example.backend.util;

import lombok.Data;

@Data
public class LoginInfoDto {
    private Long userId;
    private String email;
    private String nickname;
}