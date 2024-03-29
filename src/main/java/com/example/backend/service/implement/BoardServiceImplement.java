package com.example.backend.service.implement;

import com.example.backend.common.ResponseCode;
import com.example.backend.common.ResponseMessage;
import com.example.backend.dto.object.BoardLatestListItem;
import com.example.backend.dto.object.GrassItemDto;
import com.example.backend.dto.request.board.PatchBoardRequestDto;
import com.example.backend.dto.request.board.PostBoardRequestDto;
import com.example.backend.dto.object.BoardResponseDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.board.*;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import com.example.backend.service.BoardService;
import com.example.backend.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final BoardQueryRepository boardQueryRepository;
    private final GrassRepository grassRepository;
    private final ReviewNotifyRepository reviewNotifyRepository;
    private final NotificationRepository notificationRepository;

    /**
     * getGrassList
     *
     * @parm userId
     * @parm startDate
     * @parm endDate
     * @return ResponseEntity<? super GetGrassResponseDto>
     */
    @Override
    public ResponseEntity<? super GetGrassResponseDto> getGrassList(Long userId, String startDate, String endDate) {
        List<GrassItemDto> grassList = new ArrayList<>();
        try {
            UserEntity userEntity=userRepository.findByUserId(userId);
            if(userEntity==null) return GetGrassResponseDto.notExistUser();
            List<GrassEntity> grassEntities = grassRepository.findAllGrassList(userId, startDate, endDate);
            for(GrassEntity grassEntity:grassEntities){
                GrassItemDto grassItemDto = new GrassItemDto(grassEntity);
                grassList.add(grassItemDto);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetGrassResponseDto.success(grassList);
    }

    /**
     * findUserIdAndBoardByCreatedAtDesc
     *
     * @parm lastBoardNumber
     * @parm pageable
     * @parm searchWord
     * @parm loginUserDto
     * @return ResponseEntity<? super GetNoOffsetResponseDto>
     */
    @Override
    public ResponseEntity<? super GetNoOffsetResponseDto> findUserIdAndBoardByCreatedAtDesc(Long lastBoardNumber, Pageable pageable, String searchWord, LoginUserDto loginUserDto) {
        UserEntity userEntity = null;
        Slice<BoardResponseDto> boardResponseDtos = null;
        try {
            userEntity = userRepository.findByUserId(loginUserDto.getUserId());
            if (userEntity == null) return GetNoOffsetResponseDto.notExistUser();
            boardResponseDtos = boardQueryRepository.findBoardWithNoOffset(lastBoardNumber, pageable, searchWord, userEntity.getUserId());
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetNoOffsetResponseDto.success(boardResponseDtos);
    }

    /**
     * getLatestBoardList
     *
     * @parm userId
     * @return ResponseEntity<? super GetLatestBoardListResponseDto>
     */
    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(Long userId) {
        List<BoardEntity> boardEntities = new ArrayList<>();
        List<TagEntity> tagEntities = new ArrayList<>();
        List<BoardLatestListItem> boardLatestListItems = new ArrayList<>();
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetLatestBoardListResponseDto.notExistUser();

            boardEntities = boardRepository.findByUserIdOrderByBoardNumberDesc(userId);
            int boadCount = 0;
            for (BoardEntity boardEntity : boardEntities) {
                int boardNumber = boardEntity.getBoardNumber();
                List<String> tagList = new ArrayList<>();
                tagEntities = tagRepository.findByBoardNumber(boardNumber);
                for (TagEntity tagEntity : tagEntities) {
                    String tag = tagEntity.getTag();
                    tagList.add(tag);
                }
                BoardLatestListItem boardTagItem = new BoardLatestListItem(boardEntity, tagList);
                boardLatestListItems.add(boardTagItem);
                boadCount++;
                if (boadCount == 6) break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetLatestBoardListResponseDto.success(boardLatestListItems);
    }

    /**
     * getSearchBoardList
     *
     * @parm searchWord
     * @parm userId
     * @return ResponseEntity<? super GetSearchBoardListResponseDto>
     */
    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, Long userId) {
        List<BoardEntity> boardEntities = new ArrayList<>();
        try {

            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return GetSearchBoardListResponseDto.notExistUser();

            boardEntities = boardRepository.findAllSearch(userId, searchWord, searchWord);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSearchBoardListResponseDto.success(boardEntities);
    }

    /**
     * getUserBoardList
     *
     * @parm userId
     * @return ResponseEntity<? super GetUserBoardListResponseDto>
     */
    @Override
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(Long userId) {
        List<BoardEntity> BoardEntities = new ArrayList<>();

        try {
            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return GetUserBoardListResponseDto.notExistUser();

            BoardEntities = boardRepository.findByUserIdOrderByBoardNumberDesc(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserBoardListResponseDto.success(BoardEntities);
    }

    /**
     * deleteBoard
     *
     * @parm boardNumber
     * @parm userId
     * @return ResponseEntity<? super DeleteBoardResponseDto>
     */
    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, Long userId) {
        BoardEntity boardEntity = null;
        List<TagEntity> tagEntities = new ArrayList<>();
        List<BoardTagMapEntity> boardTagMapEntities = new ArrayList<>();
        try {
            boolean existedEmail = userRepository.existsByUserId(userId);
            if (!existedEmail) {
                return DeleteBoardResponseDto.notExistedUser();
            }

            boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) {
                return DeleteBoardResponseDto.notExistedBoard();
            }

            if (!boardEntity.getUserId().equals(userId)) {
                return DeleteBoardResponseDto.noPermission();
            }
            boardTagMapEntities = boardTagMapRepository.findByBoardEntity(boardEntity);
            boardTagMapRepository.deleteAll(boardTagMapEntities);
            tagEntities = tagRepository.findByBoardNumber(boardNumber);
            tagRepository.deleteAll(tagEntities);


            ReviewNotifyEntity reviewNotifyEntity=reviewNotifyRepository.findByUserIdAndBoardDate(userId,boardEntity.getWriteDatetime());
            reviewNotifyEntity.deleteReviewNotify(reviewNotifyEntity);
            reviewNotifyRepository.save(reviewNotifyEntity);
            if(reviewNotifyEntity.getBoardCount()==0) reviewNotifyRepository.delete(reviewNotifyEntity);

            GrassEntity grassEntity=grassRepository.findByUserIdAndGrassDate(userId,boardEntity.getWriteDatetime());
            grassEntity.deleteGrass(grassEntity);
            grassRepository.save(grassEntity);
            if(grassEntity.getPostCount()==0) grassRepository.delete(grassEntity);

            List<NotificationEntity> notificationEntities=notificationRepository.findByBoardEntity(boardEntity);
            notificationRepository.deleteAll(notificationEntities);
            boardRepository.delete(boardEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteBoardResponseDto.success();
    }

    /**
     * patchBoard
     *
     * @parm dto
     * @parm boardNumber
     * @parm userId
     * @return ResponseEntity<? super PatchBoardResponseDto>
     */
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
                boardTagMapRepository.delete(boardTagMapEntity);
            }

            tagEntities = tagRepository.findByBoardNumber(boardNumber);
            for (TagEntity tagEntity : tagEntities) {
                tagRepository.delete(tagEntity);
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

    /**
     * postBoard
     *
     * @parm boardNumber
     * @parm userId
     * @return ResponseEntity<? super GetBoardResponseDto>
     */
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

    /**
     * postBoard
     *
     * @parm dto
     * @parm userId
     * @return ResponseEntity<? super PostBoardResponseDto>
     */
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

            LocalDate date = LocalDate.now();
            String localDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            GrassEntity grassEntity = grassRepository.findByUserIdAndGrassDate(userId, localDate);
            if (grassEntity == null) {
                GrassEntity grass = new GrassEntity(userId, localDate);
                grassRepository.save(grass);
            }
            if (grassEntity != null) {
                grassEntity.updateGrass(grassEntity);
                grassRepository.save(grassEntity);
            }

            ReviewNotifyEntity reviewNotifyEntity = reviewNotifyRepository.findByUserIdAndBoardDate(userId, localDate);
            if (reviewNotifyEntity == null) {
                ReviewNotifyEntity reviewNotify = new ReviewNotifyEntity(userId, localDate);
                reviewNotifyRepository.save(reviewNotify);
            }
            if (reviewNotifyEntity != null) {
                reviewNotifyEntity.updateReviewNotify(reviewNotifyEntity);
                reviewNotifyRepository.save(reviewNotifyEntity);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }
}
