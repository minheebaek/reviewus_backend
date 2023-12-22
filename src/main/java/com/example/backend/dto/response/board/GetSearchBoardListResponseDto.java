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
public class GetSearchBoardListResponseDto extends ResponseDto {

    private List<BoardListItem> searchList;

    private GetSearchBoardListResponseDto(List<BoardEntity> boardEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BoardListItem.getList(boardEntities);
    }

    public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardEntity> boardEntities){
        GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    public static ResponseEntity<ResponseDto> noExistUser(){
        ResponseDto result = new RsponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(BAD_REQUEST).body(result);
    }
}
