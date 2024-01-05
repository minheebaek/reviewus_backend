package com.example.backend.service;


import com.example.backend.dto.request.find.PostFindPassWdVerifyIdRequestDto;
import com.example.backend.dto.request.find.PutChangePassWdRequestDto;
import com.example.backend.dto.request.user.PatchChangeNicknameRequestDto;
import com.example.backend.dto.response.find.PostFindPassWdVerifyIdResponseDto;
import com.example.backend.dto.response.find.PutChangePassWdResponseDto;
import com.example.backend.dto.response.user.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<? super PutChangePassWdResponseDto> changePassWd(PutChangePassWdRequestDto dto);
    ResponseEntity<? super PostFindPassWdVerifyIdResponseDto> verifyId(PostFindPassWdVerifyIdRequestDto dto);
    ResponseEntity<? super PatchChangeNicknameResponseDto> patchChangeNickname(PatchChangeNicknameRequestDto dto, Long userId);
    ResponseEntity<? super GetUserProfileInfoResponse> getUserProfileInfo(Long userId);
    ResponseEntity<? super GetUserProfileMainResponseDto> getUserProfileMain(Long userId);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
