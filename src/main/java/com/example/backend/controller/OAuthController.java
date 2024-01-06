package com.example.backend.controller;

import com.example.backend.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    @GetMapping("code/google")
    public ResponseEntity<String> successGoogleLogin(@RequestParam("code") String accessCode){
        return oAuthService.getGoogleAccessToken(accessCode);
    }
}
