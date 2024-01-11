package com.example.backend.repository;

import com.example.backend.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    Optional<NotificationEntity> findById(Long id);
}