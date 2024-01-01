package com.example.backend.dto.object;

import com.example.backend.entity.BoardEntity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardResponseDto {
    private int boardNumber;
    private String title;
    private String content;
    private String writeDatetime;
    private boolean alarm;

    public BoardResponseDto(BoardEntity boardEntity) {
        this.boardNumber = boardEntity.getBoardNumber();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.writeDatetime = boardEntity.getWriteDatetime();
        this.alarm = boardEntity.isAlarm();

    }
}

