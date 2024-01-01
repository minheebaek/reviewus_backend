package com.example.backend.dto.object;

import com.example.backend.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {
    private int boardNumber;
    private String title;
    private String content;
    private String writeDatetime;
    private boolean alarm;

    public BoardListItem(BoardEntity boardEntity) {
        this.boardNumber = boardEntity.getBoardNumber();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.writeDatetime = boardEntity.getWriteDatetime();
        this.alarm = boardEntity.isAlarm();
    }

    public static List<BoardListItem> getList(List<BoardEntity> boardEntities){
        List<BoardListItem> list = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntities){
            BoardListItem boardListItem = new BoardListItem(boardEntity);
            list.add(boardListItem);
        }
        return list;
    }
}
