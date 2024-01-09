package com.example.backend.dto.object;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SocialOAuthTokenDto {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;

    public SocialOAuthTokenDto(){}
}

