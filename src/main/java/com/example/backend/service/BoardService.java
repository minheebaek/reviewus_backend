package com.example.backend.service;

import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.response.board.*;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(Long userId, String searchWord);
    ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(Long userId);
    ResponseEntity<?> deleteBoard(Integer boardNumber, Long userId);
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, Long userId);
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber,Long userId);
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, Long userId);
}
