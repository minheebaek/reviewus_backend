package com.example.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "notification")
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long notifyId;

    @Column(name = "user_id")
    private Long userId;
/*    private String title;*/
/*    private String contents;        // 복습 주기 알림 내용*/

   /* private String url;

    @Column(nullable = false)
    private Boolean isRead;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_number")
    private BoardEntity boardEntity;

}