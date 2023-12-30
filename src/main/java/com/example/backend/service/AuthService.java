package com.example.backend.service;

import com.example.backend.dto.RefreshTokenDto;
import com.example.backend.dto.request.auth.PatchChangePasswdRequestDto;
import com.example.backend.dto.request.auth.SignInRequestDto;
import com.example.backend.dto.request.auth.SignUpRequestDto;
import com.example.backend.dto.response.auth.PatchChangePasswdResponseDto;
import com.example.backend.dto.response.auth.SignInResponseDto;
import com.example.backend.dto.response.auth.SignUpResponseDto;
import com.example.backend.dto.response.auth.DeleteLogoutDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super PatchChangePasswdResponseDto> changePasswd(Long userId, PatchChangePasswdRequestDto dto);
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestBody);

    ResponseEntity<? super SignInResponseDto> refreshToken(RefreshTokenDto requestBody);

    ResponseEntity<? super DeleteLogoutDto> logout(RefreshTokenDto requestBody);
}
