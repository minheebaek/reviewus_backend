package com.example.backend.dto.response.find;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PutChangePassWdResponseDto extends ResponseDto {
    private PutChangePassWdResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PutChangePassWdResponseDto> success(){
        PutChangePassWdResponseDto result = new PutChangePassWdResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> mismatchNewPassWd(){
        ResponseDto result = new ResponseDto(ResponseCode.NEW_PASSWD_FAIL, ResponseMessage.PASSWD_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
