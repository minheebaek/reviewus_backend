package com.example.backend.controller;


import com.example.backend.dto.request.user.PatchUserInfoRequestDto;
import com.example.backend.dto.response.user.GetSignInUserResponseDto;
import com.example.backend.dto.response.user.PatchUserInfoResponseDto;
import com.example.backend.service.UserService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 수정
     * localhost:8080/profile/info
     *
     * @parm requestBody
     * @parm loginUserDto
     * @return response
     */
    @PatchMapping("info")
    public ResponseEntity<? super PatchUserInfoResponseDto> patchUserInfo(
            @RequestBody @Valid PatchUserInfoRequestDto requestBody,
            @IfLogin LoginUserDto loginUserDto
            ){
        ResponseEntity<? super PatchUserInfoResponseDto> response = userService.patchUserInfo(requestBody, loginUserDto.getUserId());
        return response;
    }

    /**
     * 로그인 사용자 정보 불러오기
     * localhost:8080/profile/user
     *
     * @parm email
     * @return response
     */
    @GetMapping("/user")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(loginUserDto.getEmail());
        return response;
    }
}
