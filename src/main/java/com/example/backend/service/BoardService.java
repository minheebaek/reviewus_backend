package com.example.backend.service;

import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.response.board.*;
import com.example.backend.util.LoginUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface BoardService {
    ResponseEntity<? super GetGrassResponseDto> getGrassList(Long userId, String startDate, String endDate);

    ResponseEntity<? super GetNoOffsetResponseDto> findUserIdAndBoardByCreatedAtDesc(Long userId, Pageable pageable, String searchWord, LoginUserDto loginUserDto);

    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(Long userId);

    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, Long userId);

    ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(Long userId);

    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, Long userId);

    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, Long userId);

    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber, Long userId);

    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, Long userId);
}
