package com.example.backend.dto.response.board;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.TagEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetBoardResponseDto extends ResponseDto {
    private int boardNumber;
    private Long userId;
    private String title;
    private String content;
    private List<String> tagList;
    private String writeDatetime;
    private boolean alarm;

    private GetBoardResponseDto(BoardEntity boardEntity,  List<TagEntity> tagEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        List<String> tagList = new ArrayList<>();
        for(TagEntity tagEntity: tagEntities){
            String boardTag= tagEntity.getTag();
            tagList.add(boardTag);
        }

        this.boardNumber = boardEntity.getBoardNumber();
        this.userId = boardEntity.getUserId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.tagList = tagList;
        this.writeDatetime = boardEntity.getWriteDatetime();
        this.alarm = boardEntity.isAlarm();
    }

    public static ResponseEntity<GetBoardResponseDto> success(BoardEntity boardEntity, List<TagEntity> tagEntities){
        GetBoardResponseDto result = new GetBoardResponseDto(boardEntity, tagEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistUser(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
    public static ResponseEntity<ResponseDto> notExistBoard(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> notPermission(){
        ResponseDto result = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}
