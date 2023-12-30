package com.example.backend.dto.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class PatchChangeNicknameRequestDto {

    @NotBlank
    private String changeNickname;


}
