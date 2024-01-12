package com.example.backend.repository;

import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Optional<NotificationEntity> findById(Long id);
    List<NotificationEntity> findByBoardEntity(BoardEntity boardEntity);

    List<NotificationEntity> findByUserId(Long userId);
}