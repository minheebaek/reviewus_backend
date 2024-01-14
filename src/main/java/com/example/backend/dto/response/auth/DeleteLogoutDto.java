package com.example.backend.dto.response.auth;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class DeleteLogoutDto extends ResponseDto {
    private String oauthAccessToken;
    private DeleteLogoutDto(String oauthAccessToken){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.oauthAccessToken = oauthAccessToken;
    }

    public static ResponseEntity<DeleteLogoutDto> success(String oauthAccessToken){
        DeleteLogoutDto result = new DeleteLogoutDto(oauthAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notAuthorization(){
        ResponseDto result = new ResponseDto(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

}
