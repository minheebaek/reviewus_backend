package com.example.backend.service;

import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.notify.ReviewNotifyResponseDto;
import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.ReviewNotifyEntity;
import com.example.backend.repository.BoardRepository;
import com.example.backend.repository.ReviewNotifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewNotifyRepository reviewNotifyRepository;
    private final NotificationService notificationService;
    private final BoardRepository boardRepository;

    public ResponseEntity<? super ReviewNotifyResponseDto> notify(Long userId) {
        try {
            log.info(userId + "userId");
            List<ReviewNotifyEntity> reviewNotifyEntities = reviewNotifyRepository.findByUserId(userId);
            for (ReviewNotifyEntity reviewNotifyEntity : reviewNotifyEntities) {
                log.info("reviewNotifyEntity.getBoardDate()" + reviewNotifyEntity.getBoardDate());
                LocalDate endDate = LocalDate.now();
                String boardDate = reviewNotifyEntity.getBoardDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(boardDate, formatter);
                LocalDateTime date1 = startDate.atStartOfDay();
                LocalDateTime date2 = endDate.atStartOfDay();

                int betweenDays = (int) Duration.between(date1, date2).toDays();
                log.info("betweenDays:" + betweenDays);
                if (betweenDays == 1 || betweenDays == 8 || betweenDays == 38) {
                    List<BoardEntity> boardEntities = boardRepository.findByWriteDatetime(boardDate);
                    for (BoardEntity boardEntity : boardEntities) {
                        notificationService.send(boardEntity, betweenDays);
                    }
                }
                if (betweenDays >= 39) {
                    reviewNotifyRepository.delete(reviewNotifyEntity);
                }
            }
            }catch(Exception exception){
                exception.printStackTrace();
                return ResponseDto.databaseError();
            }
            return ReviewNotifyResponseDto.success();

        }
    }
