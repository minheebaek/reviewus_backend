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
public class BoardLatestListItem {

    private int boardNumber;
    private String title;
    private String writeDatetime;
    private List<String> tagList;

    public BoardLatestListItem(BoardEntity boardEntity, List<String> tagList) {
        this.boardNumber = boardEntity.getBoardNumber();
        this.title = boardEntity.getTitle();
        this.writeDatetime = boardEntity.getWriteDatetime();
        this.tagList = tagList;
    }

}

