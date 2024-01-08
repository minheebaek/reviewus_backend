package com.example.backend.controller;

import com.example.backend.dto.request.OAuth.PostOAuthSigninRequestDto;
import com.example.backend.dto.response.PostOAuthSigninResponseDto;
import com.example.backend.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/oauth2")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    @GetMapping("code/google")
    public ResponseEntity<String> successGoogleLogin(@RequestParam("code") String accessCode){
        return oAuthService.successGoogleLogin(accessCode);
    }

    @PostMapping("/code/google")
    public ResponseEntity<String> getGoogleAccessToken(@RequestParam("code") String accessToken){
        return oAuthService.getGoogleAccessToken(accessToken);
    }

    @PostMapping("signin/google")
    public ResponseEntity<? super PostOAuthSigninResponseDto> googleLogin(@RequestBody @Valid PostOAuthSigninRequestDto requestBody){
        ResponseEntity<? super PostOAuthSigninResponseDto> response = oAuthService.googleLogin(requestBody);
        return response;
    }

}
