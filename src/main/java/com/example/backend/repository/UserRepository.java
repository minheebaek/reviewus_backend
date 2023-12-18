package com.example.backend.repository;

import com.example.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUserId(Long userId);
    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);
    UserEntity findByUserId(Long userId);
}
