package com.example.backend.entity;

import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board")
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNumber;
    private Long userId;
    private String title;
    private String content;
    private String writeDatetime;
    private boolean alarm;
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    private List<BoardTagMapEntity> boardTagMapEntityList  = new ArrayList<>();

    public BoardEntity(PostBoardRequestDto dto, Long userId){

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.userId = userId;
        this.alarm = dto.isAlarm();
    }

    public void updateBoard(PatchBoardRequestDto dto){
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.alarm=dto.isAlarm();
    }


}
