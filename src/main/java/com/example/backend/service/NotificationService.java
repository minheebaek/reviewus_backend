package com.example.backend.service;


import com.example.backend.controller.NotificationController;
import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.NotificationEntity;
import com.example.backend.repository.BoardRepository;
import com.example.backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static Map<Long, Integer> notificationCounts = new HashMap<>();     // 알림 개수 저장
    private final NotificationRepository notificationRepository;
    private final BoardRepository boardRepository;
    public SseEmitter subscribe(Long userId) {

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        NotificationController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));

        return sseEmitter;
    }


    public void send(BoardEntity boardEntity, int betweenDays) {

        Long userId =boardEntity.getUserId();

        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = NotificationController.sseEmitters.get(userId);
            try {
                Map<String, String> eventData = new HashMap<>();
                eventData.put("title", boardEntity.getTitle());
                if(betweenDays==8||betweenDays==38){
                    betweenDays-=1;
                }
                eventData.put("content",betweenDays+"일 전에 공부 했던 내용을 다시 학습 해 보세요!");
                eventData.put("boardNumber", String.valueOf(boardEntity.getBoardNumber()));


                sseEmitter.send(SseEmitter.event().name("addComment").data(eventData));

                // DB 저장
                NotificationEntity notificationEntity = new NotificationEntity();

                notificationEntity.setUserId(userId);
                notificationEntity.setBoardEntity(boardEntity);         // post 필드 설정
                notificationRepository.save(notificationEntity);

                // 알림 개수 증가
                notificationCounts.put(userId, notificationCounts.getOrDefault(userId, 0) + 1);

                // 현재 알림 개수 전송
                sseEmitter.send(SseEmitter.event().name("notificationCount").data(notificationCounts.get(userId)));

            } catch (IOException e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }

    /*// 알림 삭제
    public MsgResponseDto deleteNotification(Long id) throws IOException {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림을 찾을 수 없습니다.")
        );

        Long userId = notification.getPost().getUser().getId();

        notificationRepository.delete(notification);

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

        return new MsgResponseDto("알림이 삭제되었습니다.", HttpStatus.OK.value());
    }*/


}
