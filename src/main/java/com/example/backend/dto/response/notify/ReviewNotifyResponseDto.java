package com.example.backend.dto.response.notify;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ReviewNotifyResponseDto extends ResponseDto {
    public ReviewNotifyResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    public static ResponseEntity<ReviewNotifyResponseDto> success(){
        ReviewNotifyResponseDto result = new ReviewNotifyResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
