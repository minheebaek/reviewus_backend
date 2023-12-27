package com.example.backend.dto.response.user;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.entity.UserEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetUserProfileMainResponseDto extends ResponseDto {
    private String profileImage;
    private String nickname;
    private String email;

    private GetUserProfileMainResponseDto(UserEntity userEntity){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.profileImage = userEntity.getProfileImage();
        this.nickname= userEntity.getEmail();
        this.email= userEntity.getEmail();
    }

    public static ResponseEntity<GetUserProfileMainResponseDto> success(UserEntity userEntity){
        GetUserProfileMainResponseDto result = new GetUserProfileMainResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
