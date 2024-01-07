package com.example.backend.dto.request.OAuth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOAuthSigninRequestDto {
    private String email;
    private String name;
    private String picture;
}
