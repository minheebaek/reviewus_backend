package com.example.backend.dto.response.board;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.object.BoardLatestListItem;
import com.example.backend.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {
    List<BoardLatestListItem> latestBoardList;

    public GetLatestBoardListResponseDto(List<BoardLatestListItem> boardLatestListItems) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestBoardList = boardLatestListItems;


    }

    public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardLatestListItem> boardLatestListItems) {
        GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardLatestListItems);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

    }
}
