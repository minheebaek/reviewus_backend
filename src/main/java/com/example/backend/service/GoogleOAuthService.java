package com.example.backend.service;

import com.example.backend.dto.object.SocialOAuthTokenDto;
import com.example.backend.dto.request.OAuth.PostOAuthSigninRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuthService {
    private final ObjectMapper objectMapper;
    RestTemplate restTemplate = new RestTemplate();
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String LOGIN_REDIRECT_URI;

    /**
     * requestGoogleAccessToken
     *
     * @parm accessCode
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> requestGoogleAccessToken(String accessCode) {


        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URI);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
        log.info(responseEntity.getStatusCode()+"= responseEntity.getStatusCode()");
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    /**
     * getGoogleAccessToken
     *
     * @parm accessTokenResponse
     * @return SocialOAuthTokenDto
     */
    public SocialOAuthTokenDto getGoogleAccessToken(ResponseEntity<String> accessTokenResponse) throws JsonProcessingException {
        return objectMapper.readValue(accessTokenResponse.getBody(),SocialOAuthTokenDto.class);
    }

    /**
     * requestUserInfo
     *
     * @parm socialOAuthTokenDto
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> requestUserInfo(SocialOAuthTokenDto socialOAuthTokenDto) {
        //header에 google의 accessToken을 담는다
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer"+socialOAuthTokenDto.getAccess_token());

        URI uri = UriComponentsBuilder.fromUriString("https://www.googleapis.com/oauth2/v2/userinfo").build().toUri();

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);


    }

    /**
     * getUserInfo
     *
     * @parm userInfoResponse
     * @return PostOAuthSigninRequestDto
     */
    public PostOAuthSigninRequestDto getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException {
        return objectMapper.readValue(userInfoResponse.getBody(), PostOAuthSigninRequestDto.class);
    }

}
