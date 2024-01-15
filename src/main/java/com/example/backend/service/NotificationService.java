package com.example.backend.service;


import com.example.backend.controller.NotificationController;
import com.example.backend.dto.response.notify.DeleteNotificationResponseDto;
import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.NotificationEntity;
import com.example.backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static Map<Long, Integer> notificationCounts = new HashMap<>();     // 알림 개수 저장
    private final NotificationRepository notificationRepository;

    /**
     * subscribe
     *
     * @parm userId
     * @return SseEmitter
     */
    public SseEmitter subscribe(Long userId) {

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException exception) {

        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        NotificationController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        LocalDate date = LocalDate.now();
        String localDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(sseEmitter, "REVIEWUS와 " + localDate + " 복습을 시작하세요!");

        return sseEmitter;
    }

    /**
     * sendToClient
     *
     * @parm emitter
     * @parm data
     * @return void
     */
    private void sendToClient(SseEmitter emitter, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .name("first sse")
                    .data(data));
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("연결 오류!");
        }
    }

    /**
     * send
     *
     * @parm boardEntity
     * @parm betweenDays
     * @return void
     */
    public void send(BoardEntity boardEntity, int betweenDays) {

        Long userId =boardEntity.getUserId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                // DB 저장
                NotificationEntity notificationEntity = new NotificationEntity();

                notificationEntity.setUserId(userId);
                notificationEntity.setBoardEntity(boardEntity);         // post 필드 설정
                notificationRepository.save(notificationEntity);

                Map<String, String> eventData = new HashMap<>();
                eventData.put("title", boardEntity.getTitle());
                if(betweenDays==8||betweenDays==38){
                    betweenDays-=1;
                }
                eventData.put("content",betweenDays+"일 전에 공부 했던 내용을 다시 학습 해 보세요!");
                eventData.put("boardNumber", String.valueOf(boardEntity.getBoardNumber()));
                eventData.put("notification_id", String.valueOf(notificationEntity.getNotifyId()));


                sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));



                // 알림 개수 증가
                notificationCounts.put(userId, notificationCounts.getOrDefault(userId, 0) + 1);

                // 현재 알림 개수 전송
                sseEmitter.send(SseEmitter.event().name("notificationCount").data(notificationCounts.get(userId)));

            } catch (IOException e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

    /**
     * deleteNotification
     *
     * @parm id
     * @return ResponseEntity<? super DeleteNotificationResponseDto>
     */
    public ResponseEntity<? super DeleteNotificationResponseDto> deleteNotification(Long id) throws IOException {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림을 찾을 수 없습니다.")
        );

        Long userId = notificationEntity.getUserId();

        notificationRepository.delete(notificationEntity);

        // 알림 개수 감소
        if (notificationCounts.containsKey(userId)) {
            int currentCount = notificationCounts.get(userId);
            if (currentCount > 0) {
                notificationCounts.put(userId, currentCount - 1);
            }
        }

        // 현재 알림 개수 전송
        SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
        sseEmitter.send(SseEmitter.event().name("notificationCount").data(notificationCounts.get(userId)));

        return DeleteNotificationResponseDto.success();
    }


}
