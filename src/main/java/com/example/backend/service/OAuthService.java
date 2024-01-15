package com.example.backend.service;

import com.example.backend.dto.object.SocialOAuthTokenDto;
import com.example.backend.dto.request.OAuth.PostOAuthSigninRequestDto;
import com.example.backend.dto.response.PostOAuthSigninResponseDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.entity.RefreshToken;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtTokenizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
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
    private final GoogleOAuthService googleOAuthService;
    private final RedisService redisService;

    public String generate() {
        return "https://accounts.google.com/o/oauth2/v2/auth" + "?"
                + "client_id=" + GOOGLE_CLIENT_ID + "&"
                + "redirect_uri=" + LOGIN_REDIRECT_URI + "&"
                + "response_type=code&"
                + "scope=" + "email" + "&"
                + "access_type=offline";
    }

    /**
     * OAuthLogin
     *
     * @parm requestBody
     * @return ResponseEntity<? super PostOAuthSigninResponseDto>
     */
    public ResponseEntity<? super PostOAuthSigninResponseDto> OAuthLogin(String requestBody) throws JsonProcessingException {
        //1.code로 json 형식의 응답객체를 받아옴
         ResponseEntity<String> accessTokenResponse=googleOAuthService.requestGoogleAccessToken(requestBody);
        //2.json 형식의 응답객체를 deserialization해서 자바객체에 담기
         SocialOAuthTokenDto socialOAuthTokenDto =googleOAuthService.getGoogleAccessToken(accessTokenResponse);
         log.info("getAccess_token"+socialOAuthTokenDto.getAccess_token());

        //3.구글이 준 액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아옴
        ResponseEntity<String> userInfoResponse =googleOAuthService.requestUserInfo(socialOAuthTokenDto);
        //4.json 형식의 응답 객체를 자바 객체로 역직렬화
        PostOAuthSigninRequestDto postOAuthSigninRequestDto=googleOAuthService.getUserInfo(userInfoResponse);
        log.info("email"+postOAuthSigninRequestDto.getEmail());
        log.info("name"+postOAuthSigninRequestDto.getName());

        String accessToken = null;
        String refreshToken = null;
        UserEntity userEntity = null;
        try {
            boolean existedEmail = userRepository.existsByEmail(postOAuthSigninRequestDto.getEmail());
            if (!existedEmail) { //강제 회원가입 진행
                userEntity = new UserEntity(postOAuthSigninRequestDto);
                userRepository.save(userEntity);
            }
            userEntity = userRepository.findByEmail(postOAuthSigninRequestDto.getEmail());
            accessToken = jwtTokenizer.createAccessToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());
            refreshToken = jwtTokenizer.createRefreshToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());

            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setValue(refreshToken);
            refreshTokenEntity.setUserId(userEntity.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);

            redisService.setDataExpire(userEntity.getEmail(),socialOAuthTokenDto.getAccess_token(),60*60);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostOAuthSigninResponseDto.success(accessToken, refreshToken, userEntity);
    }

}
