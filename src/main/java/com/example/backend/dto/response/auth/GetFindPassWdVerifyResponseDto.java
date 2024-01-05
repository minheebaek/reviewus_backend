package com.example.backend.dto.response.auth;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetFindPassWdVerifyResponseDto extends ResponseDto {
    private GetFindPassWdVerifyResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<GetFindPassWdVerifyResponseDto> success(){
        GetFindPassWdVerifyResponseDto result = new GetFindPassWdVerifyResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
