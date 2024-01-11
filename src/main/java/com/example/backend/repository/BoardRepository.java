package com.example.backend.repository;

import com.example.backend.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    BoardEntity findByBoardNumber(Integer boardNumber);

    List<BoardEntity> findByUserIdOrderByBoardNumberDesc(Long userId);
    List<BoardEntity> findByUserId(Long userId);

    List< BoardEntity> findByWriteDatetime(String boardDate);


    @Transactional
    @Query(value = "SELECT b FROM board b WHERE b.userId=:userId and (b.title LIKE %:title% OR b.content LIKE %:content% ) ORDER BY b.boardNumber DESC")
    List<BoardEntity> findAllSearch(Long userId, String title, String content);
}
