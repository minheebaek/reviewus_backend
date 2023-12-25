package com.example.backend.service;


import com.example.backend.dto.request.user.PatchUserInfoRequestDto;
import com.example.backend.dto.response.user.PatchUserInfoResponseDto;
import com.example.backend.dto.response.user.GetSignInUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super PatchUserInfoResponseDto> patchUserInfo(PatchUserInfoRequestDto dto, Long userId);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
