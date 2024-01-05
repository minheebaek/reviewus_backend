package com.example.backend.controller;

import com.example.backend.dto.request.find.PostFindPassWdVerifyIdRequestDto;
import com.example.backend.dto.response.auth.GetFindPassWdVerifyResponseDto;
import com.example.backend.dto.response.auth.PostFindPassWdResponsetDto;
import com.example.backend.dto.response.find.PostFindPassWdVerifyIdResponseDto;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.EmailService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/find")
@RequiredArgsConstructor
public class FindController {

    private final EmailService emailService;
    private final UserService userService;

    /**
     * 비밀번호 찾기 - 아이디 확인
     * localhost:8080/find/PassWd/mailConfirm
     *
     * @parm email
     * @return response
     */

    @PostMapping("/PassWd/verificationsId")
    public ResponseEntity<? super PostFindPassWdVerifyIdResponseDto> verificationId(@RequestBody @Valid PostFindPassWdVerifyIdRequestDto dto){
        ResponseEntity<? super PostFindPassWdVerifyIdResponseDto> result = userService.verifyId(dto);
        return result;
    }

    /**
     * 비밀번호 찾기 - 인증번호 발송
     * localhost:8080/find/PassWd/mailConfirm
     *
     * @parm email
     * @return response
     */

    @PostMapping("/PassWd/mailConfirm")
    @ResponseBody
    public ResponseEntity<? super PostFindPassWdResponsetDto> mailConfirm(@RequestParam String email) throws Exception {
        ResponseEntity<? super PostFindPassWdResponsetDto> response = emailService.sendSimpleMessage(email);
        return response;
    }

    /**
     * 비밀번호 찾기 - 인증번호 유효성 검증
     * localhost:8080/find/PassWd/verifications
     *
     * @parm authCode
     * @return response
     */
    @GetMapping("/PassWd/verifications")
    public ResponseEntity<? super GetFindPassWdVerifyResponseDto> mailVerification(@RequestParam("code") String authCode) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<? super GetFindPassWdVerifyResponseDto> response = emailService.verifyEmail(authCode);
        return response;
    }
}
