package com.example.backend.repository;

import com.example.backend.entity.ReviewNotifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewNotifyRepository extends JpaRepository<ReviewNotifyEntity, Long> {
    ReviewNotifyEntity findByUserIdAndBoardDate(Long userId, String localDate);

    List<ReviewNotifyEntity> findByUserId(Long userId);
}
