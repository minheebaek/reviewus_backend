package com.example.backend.service.implement;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.board.GetBoardResponseDto;
import com.example.backend.dto.response.board.GetUserBoardListResponseDto;
import com.example.backend.dto.response.board.PatchBoardResponseDto;
import com.example.backend.dto.response.board.PostBoardResponseDto;
import com.example.backend.entity.BoardEntity;
import com.example.backend.entity.BoardListViewEntity;
import com.example.backend.entity.BoardTagMapEntity;
import com.example.backend.entity.TagEntity;
import com.example.backend.repository.*;
import com.example.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final BoardTagMapRepository boardTagMapRepository;
    private final BoardListViewRepository boardListViewRepository;

    @Override
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(String email) {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try {
            boolean existedUser =userRepository.existsByEmail(email);
            if(!existedUser) return GetUserBoardListResponseDto.noExistUser();

            boardListViewEntities = boardListViewRepository.findByWriterEmailOrderByWriteDatetimeDesc(email);
        } catch (Exception exception){
            return ResponseDto.databaseError();
        }
        return GetUserBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<?> deleteBoard(Integer boardNumber, Long userId) {
        BoardEntity boardEntity = null;
        List<TagEntity> tagEntities = new ArrayList<>();
        List<BoardTagMapEntity> boardTagMapEntities = new ArrayList<>();
        try {
            boolean existedEmail = userRepository.existsByUserId(userId);
            if (!existedEmail) {
                ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            }

            boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) {
                ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

            if (!boardEntity.getUserId().equals(userId)) {
                ResponseDto result = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
            }
            boardTagMapEntities = boardTagMapRepository.findByBoardEntity(boardEntity);
            boardTagMapRepository.deleteAll(boardTagMapEntities);
            tagEntities = tagRepository.findByBoardNumber(boardNumber);
            tagRepository.deleteAll(tagEntities);
            boardRepository.delete(boardEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        ResponseDto result = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, Long userId) {
        BoardEntity boardEntity = null;
        List<TagEntity> tagEntities = new ArrayList<>();
        List<BoardTagMapEntity> boardTagMapEntities = new ArrayList<>();


        try {
            boolean existedEmail = userRepository.existsByUserId(userId);
            if (!existedEmail) return PatchBoardResponseDto.notExistUser();

            boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PatchBoardResponseDto.notExistBoard();

            if (!boardEntity.getUserId().equals(userId)) return PatchBoardResponseDto.notpermission();
            boardEntity.updateBoard(dto);
            boardRepository.save(boardEntity);
            boardTagMapEntities = boardTagMapRepository.findByBoardEntity(boardEntity);
            for (BoardTagMapEntity boardTagMapEntity : boardTagMapEntities) {

                boardTagMapEntity.setBoardEntity(null);
                boardTagMapEntity.setTagEntity(null);

            }
            List<String> tagList = dto.getTagList();
            for (String tag : tagList) {

                TagEntity tagEntity = new TagEntity(boardNumber, tag);
                tagRepository.save(tagEntity);
                boardTagMapRepository.save(new BoardTagMapEntity(tagEntity, boardEntity));

            }


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber, Long userId) {
        BoardEntity boardEntity = null;
        List<TagEntity> tagEntities = new ArrayList<>();

        try {
            boolean existedEmail = userRepository.existsByUserId(userId);
            if (!existedEmail) return GetBoardResponseDto.notExistUser();

            tagEntities = tagRepository.findByBoardNumber(boardNumber);

            boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return GetBoardResponseDto.notExistBoard();

            if (!boardEntity.getUserId().equals(userId)) return GetBoardResponseDto.notPermission();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(boardEntity, tagEntities);
    }

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, Long userId) {
        try {
            boolean existedEmail = userRepository.existsByUserId(userId);
            if (!existedEmail) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, userId);
            boardRepository.save(boardEntity);

            int boardNumber = boardEntity.getBoardNumber();
            List<String> tagList = dto.getTagList();
            List<TagEntity> tagEntities = new ArrayList<>();

            List<BoardTagMapEntity> boardTagMapEntities = new ArrayList<>();

            for (String tag : tagList) {
                TagEntity tagEntity = new TagEntity(boardNumber, tag);
                BoardTagMapEntity boardTagMapEntity = new BoardTagMapEntity();
                tagEntities.add(tagEntity);
                boardTagMapEntity.setBoardEntity(boardEntity);
                boardTagMapEntity.setTagEntity(tagEntity);
                boardTagMapEntities.add(boardTagMapEntity);
            }

            tagRepository.saveAll(tagEntities);
            boardTagMapRepository.saveAll(boardTagMapEntities);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }
}
