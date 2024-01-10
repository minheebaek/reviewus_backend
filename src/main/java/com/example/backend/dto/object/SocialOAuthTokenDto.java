package com.example.backend.dto.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialOAuthTokenDto {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
}

