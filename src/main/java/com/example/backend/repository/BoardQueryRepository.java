package com.example.backend.repository;


import com.example.backend.dto.object.BoardResponseDto;
import com.example.backend.entity.BoardEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.backend.entity.QBoardEntity.boardEntity;


@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<BoardResponseDto> findBoardWithNoOffset(Long lastBoardNumber, Pageable pageable, String searchWord, Long userId) {
        List<BoardEntity> result = queryFactory
                .selectFrom(boardEntity)
                .where(ltBoardNumber(lastBoardNumber), keywordContains(searchWord),boardEntity.userId.eq(userId))
                .orderBy(boardEntity.boardNumber.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<BoardResponseDto> content = new ArrayList<>();
        for (BoardEntity boardEntity : result) {
            content.add(new BoardResponseDto(boardEntity));
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true; //haseNext가 true면 last가 false
        }
        return new SliceImpl<>(content, pageable, hasNext);


    }

    private BooleanExpression keywordContains(String searchWord) {
        return ObjectUtils.isEmpty(searchWord)? null: boardEntity.title.contains(searchWord).or(boardEntity.content.contains(searchWord));
    }

    private BooleanExpression ltBoardNumber(Long auctionId) {
        return auctionId == null ? null : boardEntity.boardNumber.lt(auctionId);
    }


}