package com.example.backend.service;

import com.example.backend.dto.request.OAuth.PostOAuthSigninRequestDto;
import com.example.backend.dto.response.PostOAuthSigninResponseDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.entity.RefreshToken;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String LOGIN_REDIRECT_URI;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenizer jwtTokenizer;

    public ResponseEntity<String> successGoogleLogin(String code) {
        System.out.println("code:" + code);
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    public ResponseEntity<String> getGoogleAccessToken(String accessCode) {

        RestTemplate restTemplate=new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URI);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_URL, params,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }
        return null;
    }

    public ResponseEntity<? super PostOAuthSigninResponseDto> googleLogin(PostOAuthSigninRequestDto requestBody) {
        String accessToken = null;
        String refreshToken = null;
        UserEntity userEntity = null;
        try {
            boolean existedEmail = userRepository.existsByEmail(requestBody.getEmail());
            if (!existedEmail) { //강제 회원가입 진행
                userEntity = new UserEntity(requestBody);
                userRepository.save(userEntity);
            }
            userEntity = userRepository.findByEmail(requestBody.getEmail());
            accessToken = jwtTokenizer.createAccessToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());
            refreshToken = jwtTokenizer.createRefreshToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());

            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setValue(refreshToken);
            refreshTokenEntity.setUserId(userEntity.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostOAuthSigninResponseDto.success(accessToken, refreshToken, userEntity);
    }

}
