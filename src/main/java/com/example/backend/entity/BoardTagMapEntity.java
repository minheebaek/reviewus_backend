package com.example.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "boardtagmap")
@Table(name = "boardtagmap")
public class BoardTagMapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_tag_number")
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_number")
    private BoardEntity boardEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_number")
    private TagEntity tagEntity;


    public BoardTagMapEntity (TagEntity tagEntity, BoardEntity boardEntity) {
        this.tagEntity = tagEntity;
        this.boardEntity = boardEntity;
    }
}

