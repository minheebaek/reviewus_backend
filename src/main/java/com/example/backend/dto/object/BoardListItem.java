package com.example.backend.dto.object;

import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.BoardListViewEntity;
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
    //private String[] tagList;

    public BoardListItem(BoardEntity boardListViewEntity) {
        this.boardNumber = boardListViewEntity.getBoardNumber();
        this.title = boardListViewEntity.getTitle();
        this.content = boardListViewEntity.getContent();
        this.writeDatetime = boardListViewEntity.getWriteDatetime();
        this.alarm = boardListViewEntity.isAlarm();
    }

    public static List<BoardListItem> getList(List<BoardEntity> BoardEntities){
        List<BoardListItem> list = new ArrayList<>();
        for(BoardEntity boardEntity : BoardEntities){
            BoardListItem boardListItem = new BoardListItem(boardEntity);
            list.add(boardListItem);
        }
        return list;
    }
}
