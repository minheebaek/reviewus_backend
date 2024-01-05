package com.example.backend.dto.response.find;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostFindPassWdResponsetDto extends ResponseDto {

    private PostFindPassWdResponsetDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<PostFindPassWdResponsetDto> success(){
        PostFindPassWdResponsetDto result = new PostFindPassWdResponsetDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
