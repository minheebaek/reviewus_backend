package com.example.backend.service.implement;


import com.example.backend.dto.request.user.PatchUserInfoRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.user.GetSignInUserResponseDto;
import com.example.backend.dto.response.user.GetUserProfileInfoResponse;
import com.example.backend.dto.response.user.GetUserProfileMainResponseDto;
import com.example.backend.dto.response.user.PatchUserInfoResponseDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
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

    @Transactional
    @Override
    public ResponseEntity<? super PatchUserInfoResponseDto> patchUserInfo(PatchUserInfoRequestDto dto, Long userId) {
        try{
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity==null) return PatchUserInfoResponseDto.notExistUser();


            String nickname = dto.getNickname();
            String password = passwordEncoder.encode(dto.getPassword());
            userEntity.setUserInfo(nickname,password);
            userRepository.save(userEntity);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchUserInfoResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetUserProfileInfoResponse> getUserProfileInfo(Long userId) {
        UserEntity userEntity =null;
        try{
            userEntity = userRepository.findByUserId(userId);
            if(userEntity==null) return GetUserProfileInfoResponse.notExistUser();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserProfileInfoResponse.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetUserProfileMainResponseDto> getUserProfileMain(Long userId) {
        UserEntity userEntity = null;
        try{
            userEntity = userRepository.findByUserId(userId);
            if(userEntity==null) return GetUserProfileMainResponseDto.notExistUser();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserProfileMainResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;
        try{
            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetSignInUserResponseDto.notExistUser();
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }
}
