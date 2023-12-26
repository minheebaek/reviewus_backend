package com.example.backend.controller;


import com.example.backend.dto.request.user.PatchUserInfoRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.user.GetSignInUserResponseDto;
import com.example.backend.dto.response.user.GetUserProfileInfoResponse;
import com.example.backend.dto.response.user.PatchUserInfoResponseDto;
import com.example.backend.dto.response.user.PutUserInfoImageResponseDto;
import com.example.backend.service.UserService;
import com.example.backend.service.implement.S3ImageService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3ImageService s3ImageService;

    /**
     * 사용자 프로필 이미지 수정
     * localhost:8080/profile/info
     *
     * @parm image
     * @parm loginUserDto
     * @return PutUserInfoImageResponseDto
     */
    @PutMapping("/info/image")
    public ResponseEntity<? super PutUserInfoImageResponseDto> imageUpload(
            @IfLogin LoginUserDto loginUserDto,
            @RequestPart(name = "file") MultipartFile image) {

        if (image.isEmpty()) {
            /*return ResponseEntity.badRequest().body(false);*/
            return PutUserInfoImageResponseDto.notPermission();
        }
        try {
            // 파일 업로드 성공 시 true를 반환
            ResponseEntity<? super PutUserInfoImageResponseDto> response= s3ImageService.uploadImage(loginUserDto.getUserId(),image, "profileImage");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 업로드 실패 시 500 Internal Server Error와 false 반환
            return ResponseDto.databaseError();
        }
    }

    /**
     * 사용자 프로필 정보 수정
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
     * 사용자 프로필 정보 불러오기
     * localhost:8080/profile/info
     *
     * @parm loginUserDto
     * @return response
     */

    @GetMapping("/info")
    public ResponseEntity<?super GetUserProfileInfoResponse> getUserProfileInfo(
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetUserProfileInfoResponse> response = userService.getUserProfileInfo(loginUserDto.getUserId());
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
