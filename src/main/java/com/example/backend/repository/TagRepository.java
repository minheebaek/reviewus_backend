package com.example.backend.repository;

import com.example.backend.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    List<TagEntity> findByBoardNumber(Integer boardNumber);



}
