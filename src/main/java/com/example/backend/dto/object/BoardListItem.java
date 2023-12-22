package com.example.backend.dto.object;

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
    //private String[] tagList;

    public BoardListItem(BoardListViewEntity boardListViewEntity) {
        this.boardNumber = boardListViewEntity.getBoardNumber();
        this.title = boardListViewEntity.getTitle();
        this.content = boardListViewEntity.getContent();
        this.writeDatetime = boardListViewEntity.getWriteDatetime();
    }

    public static List<BoardListItem> getList(List<BoardListViewEntity> boardListViewEntities){
        List<BoardListItem> list = new ArrayList<>();
        for(BoardListViewEntity boardListViewEntity : boardListViewEntities){
            BoardListItem boardListItem = new BoardListItem(boardListViewEntity);
            list.add(boardListItem);
        }
        return list;
    }
}
