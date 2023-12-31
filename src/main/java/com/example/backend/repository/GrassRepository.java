package com.example.backend.repository;

import com.example.backend.entity.GrassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GrassRepository extends JpaRepository<GrassEntity,Long> {
    GrassEntity findByUserIdAndGrassDate(Long userId, String grassDate);

    @Transactional
    @Query(value = "SELECT g FROM Grass g WHERE g.userId=:userId and (g.grassDate >= :startDate and g.grassDate <= :endDate) order by g.grassDate asc")
    List<GrassEntity> findAllGrassList(Long userId, String startDate, String endDate);

    List<GrassEntity> findByUserId(Long userId);
}
