package com.example.backend.dto.response;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.entity.UserEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostOAuthSigninResponseDto extends ResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long userId;
    private String nickname;

    private PostOAuthSigninResponseDto(String accessToken, String refreshToken, UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userEntity.getUserId();
        this.nickname = userEntity.getNickname();
    }

    public static ResponseEntity<PostOAuthSigninResponseDto> success(String accessToken, String refreshToken, UserEntity userEntity) {
        PostOAuthSigninResponseDto result = new PostOAuthSigninResponseDto(accessToken, refreshToken, userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
