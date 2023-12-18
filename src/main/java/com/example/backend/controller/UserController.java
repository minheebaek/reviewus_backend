package com.example.backend.controller;

import com.example.backend.dto.response.user.GetSignInUserResponseDto;
import com.example.backend.service.UserService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인 사용자 정보 불러오기
     * localhost:8080/user
     *
     * @parm email
     * @return response
     */
    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(loginUserDto.getEmail());
        return response;
    }
}
