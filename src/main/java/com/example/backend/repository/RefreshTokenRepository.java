package com.example.backend.repository;

import com.example.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByValue(String value);
    List<RefreshToken> findByUserId(Long userId);
}