package com.example.backend.controller;

import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.response.board.*;
import com.example.backend.service.BoardService;
import com.example.backend.util.IfLogin;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mystudy")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 잔디 불러오기
     * localhost:8080/mystudy/grass/?startDate={startDate}&endDate={endDate}
     *
     * @parm startDate
     * @parm endDate
     * @parm loginUserDto
     * @return response
     */
    @GetMapping("/grass")
    public ResponseEntity<? super GetGrassResponseDto> grassList(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetGrassResponseDto> response = boardService.getGrassList(loginUserDto.getUserId(), startDate, endDate);
        return response;
    }

    /**
     * 무한스크롤(검색) no offset
     * localhost:8080/mystudy/nooffset/?size={size}&lastBoardNumber={lastBoardNumber}&searchWord={searchWord}
     *
     * @parm pageable
     * @parm lastBoardNumber
     * @parm searchWord
     * @parm loginUserDto
     * @return ResponseEntity
     */
    @GetMapping("/nooffset")
    public ResponseEntity<? super GetNoOffsetResponseDto> searchAllBoards(
            Pageable pageable,
            @RequestParam(value = "lastBoardNumber", required = false) Long lastBoardNumber,
            @RequestParam(value = "searchWord", required = false) String searchWord,
            @IfLogin LoginUserDto loginUserDto
               ) {
        ResponseEntity<? super GetNoOffsetResponseDto> result=boardService.findUserIdAndBoardByCreatedAtDesc(lastBoardNumber,pageable,searchWord,loginUserDto);
        return result;
    }

    /**
     * 특정 유저 검색 게시글 불러오기
     * localhost:8080/mystudy/search-list/{searchWord}
     *
     * @parm loginUserDto
     * @parm searchWord
     * @return response
     */
    @GetMapping("/search-list/{searchWord}")
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
            @PathVariable("searchWord") String searchWord, @IfLogin LoginUserDto loginUserDto
    ){
        System.out.println("/search-list/{searchWord}"+loginUserDto.getUserId()+"searchWord"+searchWord);
        ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord,loginUserDto.getUserId());
        return response;
    }

    /**
     * 특정 유저 게시글 불러오기
     * localhost:8080/mystudy
     *
     * @parm loginUserDto
     * @return response
     */
    @GetMapping("")
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super GetUserBoardListResponseDto> response = boardService.getUserBoardList(loginUserDto.getUserId());
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
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(
            @PathVariable ("boardNumber") Integer boardNumber,
            @IfLogin LoginUserDto loginUserDto
    ){
        ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(boardNumber, loginUserDto.getUserId());
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

