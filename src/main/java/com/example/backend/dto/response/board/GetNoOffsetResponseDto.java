package com.example.backend.dto.response.board;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.object.BoardResponseDto;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetNoOffsetResponseDto extends ResponseDto {
    private Slice<BoardResponseDto> noOffsetBoardlist;

    private GetNoOffsetResponseDto(Slice<BoardResponseDto> noOffsetBoardlist){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.noOffsetBoardlist=noOffsetBoardlist;
    }

    public static ResponseEntity<GetNoOffsetResponseDto> success(Slice<BoardResponseDto> noOffsetBoardlist){
        GetNoOffsetResponseDto result = new GetNoOffsetResponseDto(noOffsetBoardlist);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
