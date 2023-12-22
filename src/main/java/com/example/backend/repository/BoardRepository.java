package com.example.backend.repository;

import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.BoardListViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    BoardEntity findByBoardNumber (Integer boardNumber);

    List<BoardEntity> findByUserIdOrderByBoardNumberDesc(Long userId);

    List<BoardEntity> findByTitleContainsOrContentContainsOrderByBoardNumberDesc(String title, String content );
}
