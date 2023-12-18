package com.example.backend.repository;

import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.BoardTagMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagMapRepository extends JpaRepository<BoardTagMapEntity, Integer> {


    List<BoardTagMapEntity> findByBoardEntity(BoardEntity boardEntity);

}