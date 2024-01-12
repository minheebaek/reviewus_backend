package com.example.backend.dto.response.notify;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeleteNotificationResponseDto extends ResponseDto {

    public DeleteNotificationResponseDto(){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    public static ResponseEntity<DeleteNotificationResponseDto> success(){
        DeleteNotificationResponseDto result = new DeleteNotificationResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
