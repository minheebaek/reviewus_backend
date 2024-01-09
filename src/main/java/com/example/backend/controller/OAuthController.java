package com.example.backend.controller;

import com.example.backend.dto.request.OAuth.PostOAuthSigninRequestDto;
import com.example.backend.dto.response.PostOAuthSigninResponseDto;
import com.example.backend.service.OAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/oauth2")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("generate-uri")
    public String test(){
        return oAuthService.generate();
    }


    @GetMapping("/code/google")
    public ResponseEntity<? super PostOAuthSigninResponseDto> OAuthLogin(@RequestParam("code") String accessToken) throws JsonProcessingException {
        return oAuthService.OAuthLogin(accessToken);
    }

}
