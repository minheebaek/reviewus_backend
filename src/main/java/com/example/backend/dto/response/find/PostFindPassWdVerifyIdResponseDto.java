package com.example.backend.dto.response.find;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostFindPassWdVerifyIdResponseDto extends ResponseDto {
    private String email;
    private PostFindPassWdVerifyIdResponseDto(String email) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.email = email;
    }

    public static ResponseEntity<PostFindPassWdVerifyIdResponseDto> success(String email) {
        PostFindPassWdVerifyIdResponseDto result = new PostFindPassWdVerifyIdResponseDto(email);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
