package com.example.backend.dto;

import lombok.Data;

@Data
public class LoginInfoDto {
    private Long memberId;
    private String email;
    private String nickname;
}
