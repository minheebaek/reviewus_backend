package com.example.backend.repository;

import com.example.backend.entity.GrassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<GrassEntity,Long> {
    GrassEntity findByUserIdAndGrassDate(Long userId, String grassDate);
}
