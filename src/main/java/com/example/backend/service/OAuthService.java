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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenizer jwtTokenizer;

    public ResponseEntity<String> getGoogleAccessToken(String code) {
        System.out.println("code:" + code);
        return ResponseEntity.status(HttpStatus.OK).body(code);
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
