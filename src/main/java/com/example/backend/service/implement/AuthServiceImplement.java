package com.example.backend.service.implement;

import com.example.backend.dto.RefreshTokenDto;
import com.example.backend.dto.request.auth.SignInRequestDto;
import com.example.backend.dto.request.auth.SignUpRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.auth.DeleteLogoutDto;
import com.example.backend.dto.response.auth.SignInResponseDto;
import com.example.backend.dto.response.auth.SignUpResponseDto;
import com.example.backend.entity.RefreshToken;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthService;
import com.example.backend.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * refreshToken 재발급
     *
     * @return ResponseEntity<? super SignInResponseDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> refreshToken(RefreshTokenDto dto) {
        String accessToken = null;
        RefreshToken refreshToken = null;
        UserEntity userEntity = null;
        try {
            refreshToken = refreshTokenRepository.findByValue(dto.getRefreshToken());
            if (refreshToken == null) return SignInResponseDto.notAuthorization();
            Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
            Long userId = Long.valueOf((Integer) claims.get("userId"));

            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return SignInResponseDto.notExistUser();

            String email = claims.getSubject();
            accessToken = jwtTokenizer.createAccessToken(userId, email, userEntity.getNickname());


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(accessToken, dto.getRefreshToken(), userEntity);
    }

    /**
     * logout
     *
     * @return ResponseEntity<? super DeleteLogoutDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super DeleteLogoutDto> logout(RefreshTokenDto dto) {
        try {
            RefreshToken refreshToken = refreshTokenRepository.findByValue(dto.getRefreshToken());
            if(refreshToken == null) return DeleteLogoutDto.notAuthorization();
            refreshTokenRepository.delete(refreshToken);
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteLogoutDto.success();
    }

    /**
     * signIn
     *
     * @return ResponseEntity<? super SignInResponseDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String accessToken = null;
        String refreshToken = null;
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(dto.getEmail());
            if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
                return SignInResponseDto.signInFailed();
            }

            // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
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
        return SignInResponseDto.success(accessToken, refreshToken, userEntity);

    }


    /**
     * signUp
     *
     * @return ResponseEntity<? super SignUpResponseDto>
     * @parm requestBody
     */
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestBody) {
        try {
            String email = requestBody.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            String password = requestBody.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            requestBody.setPassword(encodePassword);

            UserEntity userEntity = new UserEntity(requestBody);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }


}