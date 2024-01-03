package com.example.backend.dto.response.board;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.object.GrassItemDto;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetGrassResponseDto extends ResponseDto {
    private List<GrassItemDto> grassList;

    private GetGrassResponseDto(List<GrassItemDto> grassList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.grassList = grassList;
    }

    public static ResponseEntity<GetGrassResponseDto> success(List<GrassItemDto> grassList){
        GetGrassResponseDto result = new GetGrassResponseDto(grassList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
