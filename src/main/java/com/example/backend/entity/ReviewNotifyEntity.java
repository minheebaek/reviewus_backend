package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reviewnotify")
public class ReviewNotifyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyId;

    @Column(name = "board_date")
    private String boardDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "board_count")
    private int boardCount;

    private LocalDate readDate;

    public ReviewNotifyEntity(Long userId, String localDate) {
        this.userId=userId;
        this.boardDate=localDate;
        this.boardCount=1;
    }

    public void updateReviewNotify(ReviewNotifyEntity reviewNotifyEntity) {
        this.boardCount=reviewNotifyEntity.getBoardCount()+1;
    }
    public void deleteReviewNotify(ReviewNotifyEntity reviewNotifyEntity){
        this.boardCount=reviewNotifyEntity.getBoardCount()-1;
    }
}