package com.example.backend.service.implement;


import com.example.backend.dto.request.user.PatchChangeNicknameRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.user.*;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardTagMapRepository boardTagMapRepository;



    @Transactional
    @Override
    public ResponseEntity<? super PatchChangeNicknameResponseDto> patchChangeNickname(PatchChangeNicknameRequestDto dto, Long userId) {
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return PatchChangeNicknameResponseDto.notExistUser();


            String changeNickname = dto.getChangeNickname();

            userEntity.setChangeNickname(changeNickname);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchChangeNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetUserProfileInfoResponse> getUserProfileInfo(Long userId) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetUserProfileInfoResponse.notExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserProfileInfoResponse.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetUserProfileMainResponseDto> getUserProfileMain(Long userId) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetUserProfileMainResponseDto.notExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserProfileMainResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }
}
