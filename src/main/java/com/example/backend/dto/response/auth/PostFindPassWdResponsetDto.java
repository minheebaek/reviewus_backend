package com.example.backend.dto.response.auth;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostFindPassWdResponsetDto extends ResponseDto {
    private String code;

    private PostFindPassWdResponsetDto(String ePw){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.code = code;
    }

    public static ResponseEntity<PostFindPassWdResponsetDto> success(String ePw){
        PostFindPassWdResponsetDto result = new PostFindPassWdResponsetDto(ePw);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
