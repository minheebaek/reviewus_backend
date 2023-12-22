package com.example.backend.dto.response.board;


import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.object.BoardListItem;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.entity.BoardEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetUserBoardListResponseDto extends ResponseDto {
    private List<BoardListItem> userBoardList;

    private GetUserBoardListResponseDto(List<BoardEntity> BoardEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userBoardList = BoardListItem.getList(BoardEntities);

    }

    public static ResponseEntity<GetUserBoardListResponseDto> success(List<BoardEntity> BoardEntities){
        GetUserBoardListResponseDto result = new GetUserBoardListResponseDto(BoardEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
