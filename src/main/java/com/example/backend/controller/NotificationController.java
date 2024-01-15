package com.example.backend.controller;

import com.example.backend.dto.response.notify.DeleteNotificationResponseDto;
import com.example.backend.service.NotificationService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    /**
     * 알림 연결
     * localhost:8080/notifications/subscribe
     *
     * @parm loginUserDto
     * @return response
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> subscribe(@IfLogin LoginUserDto loginUserDto){
        return ResponseEntity.ok(notificationService.subscribe(loginUserDto.getUserId()));
    }

    /**
     * 알림 삭제
     * localhost:8080/notifications/api/notification/delete/{id}
     *
     * @parm id
     * @return response
     */
    @DeleteMapping("/api/notification/delete/{id}")
    public ResponseEntity<? super DeleteNotificationResponseDto> deleteNotification(@PathVariable Long id) throws IOException {
        ResponseEntity<? super DeleteNotificationResponseDto> response = notificationService.deleteNotification(id);
        return response;
    }

}
