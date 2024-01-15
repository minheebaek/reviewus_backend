package com.example.backend.service.implement;


import com.example.backend.dto.request.find.PostFindPassWdVerifyIdRequestDto;
import com.example.backend.dto.request.find.PutChangePassWdRequestDto;
import com.example.backend.dto.request.user.PatchChangeNicknameRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.find.PostFindPassWdVerifyIdResponseDto;
import com.example.backend.dto.response.find.PutChangePassWdResponseDto;
import com.example.backend.dto.response.user.*;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * changePassWd
     *
     * @parm dto
     * @return ResponseEntity<? super PutChangePassWdResponseDto>
     */
    @Override
    public ResponseEntity<? super PutChangePassWdResponseDto> changePassWd(PutChangePassWdRequestDto dto) {
        try{
           if(dto.getNewPasswd().equals(dto.getCheckPasswd())==false){
               return PutChangePassWdResponseDto.mismatchNewPassWd();
           }
           String newPassWd = passwordEncoder.encode(dto.getNewPasswd());
           UserEntity userEntity=userRepository.findByEmail(dto.getEmail());
           userEntity.setChangePasswd(newPassWd);
           userRepository.save(userEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutChangePassWdResponseDto.success();
    }

    /**
     * verifyId
     *
     * @parm dto
     * @return ResponseEntity<? super PostFindPassWdVerifyIdResponseDto>
     */
    @Override
    public ResponseEntity<? super PostFindPassWdVerifyIdResponseDto> verifyId(PostFindPassWdVerifyIdRequestDto dto) {
        String email = null;
        try{
            UserEntity userEntity=userRepository.findByEmail(dto.getEmail());
            if(userEntity==null) return PostFindPassWdVerifyIdResponseDto.notExistUser();
            email = userEntity.getEmail();
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostFindPassWdVerifyIdResponseDto.success(email);
    }

    /**
     * patchChangeNickname
     *
     * @parm dto
     * @parm userId
     * @return ResponseEntity<? super PatchChangeNicknameResponseDto>
     */
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

    /**
     * GetUserProfileInfoResponse
     *
     * @parm userId
     * @return ResponseEntity<? super GetUserProfileInfoResponse>
     */
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

    /**
     * GetUserProfileMainResponseDto
     *
     * @parm userId
     * @return ResponseEntity<? super GetUserProfileMainResponseDto>
     */
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

    /**
     * GetSignInUserResponseDto
     *
     * @parm email
     * @return ResponseEntity<? super GetSignInUserResponseDto>
     */
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
