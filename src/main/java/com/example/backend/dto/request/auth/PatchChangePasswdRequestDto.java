package com.example.backend.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchChangePasswdRequestDto {
    @NotNull
    private String currentPasswd;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$") // 영문, 특수문자 8자 이상 20자 이하
    private String newPasswd;
    @NotNull
    private String checkPasswd;
}
