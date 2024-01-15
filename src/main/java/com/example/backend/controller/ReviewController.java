package com.example.backend.controller;

import com.example.backend.dto.response.notify.ReviewNotifyResponseDto;
import com.example.backend.service.ReviewService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviewnotify")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 알림 발송
     * localhost:8080/reviewnotify/send
     *
     * @parm loginUserDto
     * @return response
     */
    @GetMapping("/send")
    public ResponseEntity<? super ReviewNotifyResponseDto> reviewNotifySend(@IfLogin LoginUserDto loginUserDto){
        ResponseEntity<? super ReviewNotifyResponseDto> response = reviewService.notify(loginUserDto.getUserId());
        return response;
    }
}
