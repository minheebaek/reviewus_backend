package com.example.backend.dto.response.auth;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PatchChangePasswdResponseDto extends ResponseDto {
    private PatchChangePasswdResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PatchChangePasswdResponseDto> success(){
        PatchChangePasswdResponseDto result = new PatchChangePasswdResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    public static ResponseEntity<ResponseDto> mismatchCurrentPasswd(){
        ResponseDto result = new ResponseDto(ResponseCode.PASSWD_FAIL,ResponseMessage.PASSWD_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    public static ResponseEntity<ResponseDto> mismatchNewPasswd(){
        ResponseDto result = new ResponseDto(ResponseCode.NEW_PASSWD_FAIL, ResponseMessage.NEW_PASSWD_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
