package com.example.backend.service;


import com.example.backend.dto.request.user.PatchUserInfoRequestDto;
import com.example.backend.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super DeleteUserResponseDto> deleteUser(Long userId);
    ResponseEntity<? super PatchUserInfoResponseDto> patchUserInfo(PatchUserInfoRequestDto dto, Long userId);
    ResponseEntity<? super GetUserProfileInfoResponse> getUserProfileInfo(Long userId);
    ResponseEntity<? super GetUserProfileMainResponseDto> getUserProfileMain(Long userId);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
