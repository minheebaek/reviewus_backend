package com.example.backend.controller;

import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.response.board.GetBoardResponseDto;
import com.example.backend.dto.response.board.GetUserBoardListResponseDto;
import com.example.backend.dto.response.board.PatchBoardResponseDto;
import com.example.backend.dto.response.board.PostBoardResponseDto;
import com.example.backend.service.BoardService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mystudy")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/user-board-list/{email}")
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
            @PathVariable("email") String email
    ){
        ResponseEntity<? super GetUserBoardListResponseDto> response = boardService.getUserBoardList(email);
        return response;
    }
    /**
     * 게시글 삭제
     * localhost:8080/mystudy/{boardNumber}
     *
     * @parm boardNumber
     * @parm email
     * @return response
     */
    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable ("boardNumber") Integer boardNumber,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<?> response = boardService.deleteBoard(boardNumber, loginUserDto.getUserId());
        return response;
    }


    /**
     * 게시글 수정
     * localhost:8080/mystudy/{boardNumber}
     *
     * @parm requestBody
     * @parm boardNumber
     * @parm email
     * @return response
     */
    @PatchMapping("/{boardNumber}")
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(
            @RequestBody @Valid PatchBoardRequestDto requestBody,
            @PathVariable("boardNumber") Integer boardNumber,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(requestBody, boardNumber, loginUserDto.getUserId());
        return response;
    }

    /**
     * 게시글 상세보기
     * localhost:8080/mystudy/{boardNumber}
     *
     * @parm boardNumber
     * @return response
     */
    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("boardNumber") Integer boardNumber,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber, loginUserDto.getUserId());
        return response;
    }

    /**
     * 게시글 작성
     * localhost:8080/mystudy/create
     *
     * @parm requestBody
     * @parm email
     * @return response
     */
    @PostMapping("/create")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
            @RequestBody @Valid PostBoardRequestDto requestBody,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, loginUserDto.getUserId());
        return response;
    }
}

