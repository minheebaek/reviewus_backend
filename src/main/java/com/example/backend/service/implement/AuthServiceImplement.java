package com.example.backend.service.implement;

import com.example.backend.dto.RefreshTokenDto;
import com.example.backend.dto.request.auth.PatchChangePasswdRequestDto;
import com.example.backend.dto.request.auth.SignInRequestDto;
import com.example.backend.dto.request.auth.SignUpRequestDto;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.auth.DeleteLogoutDto;
import com.example.backend.dto.response.auth.PatchChangePasswdResponseDto;
import com.example.backend.dto.response.auth.SignInResponseDto;
import com.example.backend.dto.response.auth.SignUpResponseDto;
import com.example.backend.dto.response.user.DeleteUserResponseDto;
import com.example.backend.entity.*;
import com.example.backend.repository.*;
import com.example.backend.service.AuthService;
import com.example.backend.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final BoardRepository boardRepository;
    private final BoardTagMapRepository boardTagMapRepository;
    private final TagRepository tagRepository;
    private final GrassRepository grassRepository;

    @Override
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(Long userId) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return DeleteUserResponseDto.notExistUser();

            List<RefreshToken> refreshTokens =refreshTokenRepository.findByUserIdOrderByIdDesc(userId);
            refreshTokenRepository.deleteAll(refreshTokens);

            List<GrassEntity> grassEntities = grassRepository.findByUserId(userId);
            grassRepository.deleteAll(grassEntities);

            List<BoardEntity> boardEntities = boardRepository.findByUserId(userId);
            for(BoardEntity boardEntity : boardEntities){
                List<BoardTagMapEntity> boardTagMapEntities=boardTagMapRepository.findByBoardEntity(boardEntity);
                boardTagMapRepository.deleteAll(boardTagMapEntities);

                List<TagEntity> tagEntities=tagRepository.findByBoardNumber(boardEntity.getBoardNumber());
                tagRepository.deleteAll(tagEntities);
            }
            boardRepository.deleteAll(boardEntities);

            userRepository.delete(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return DeleteUserResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchChangePasswdResponseDto> changePasswd(Long userId, PatchChangePasswdRequestDto dto) {
        UserEntity userEntity = null;
        List<RefreshToken> refreshTokens =null;
        try{
            userEntity = userRepository.findByUserId(userId);
            if(userEntity==null) return PatchChangePasswdResponseDto.notExistUser();

            //1.현재 비밀번호 맞는지 체크
            if(!passwordEncoder.matches(dto.getCurrentPasswd(),userEntity.getPassword()))
            return PatchChangePasswdResponseDto.mismatchCurrentPasswd();

            //2.새 비밀번호, 새 비밀번호 확인 맞는지 체크
            if(dto.getNewPasswd().equals(dto.getCheckPasswd())==false)
                return PatchChangePasswdResponseDto.mismatchNewPasswd();

            //3.DB 비밀번호 변경
            String newPasswd = passwordEncoder.encode(dto.getNewPasswd());

            userEntity.setChangePasswd(newPasswd);
            userRepository.save(userEntity);

            //4.비밀번호 변경 성공 시 로그아웃
            refreshTokens=refreshTokenRepository.findByUserIdOrderByIdDesc(userId);
            for(RefreshToken refreshToken : refreshTokens){
                refreshTokenRepository.delete(refreshToken);
                break;
            }

        }catch (Exception exception){
            exception.printStackTrace();

        }
        return PatchChangePasswdResponseDto.success();
    }

    /**
     * refreshToken 재발급
     *
     * @return ResponseEntity<? super SignInResponseDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> refreshToken(RefreshTokenDto dto) {
        String accessToken = null;
        RefreshToken refreshToken = null;
        UserEntity userEntity = null;
        try {
            refreshToken = refreshTokenRepository.findByValue(dto.getRefreshToken());
            if (refreshToken == null) return SignInResponseDto.notAuthorization();
            Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
            Long userId = Long.valueOf((Integer) claims.get("userId"));

            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return SignInResponseDto.notExistUser();

            String email = claims.getSubject();
            accessToken = jwtTokenizer.createAccessToken(userId, email, userEntity.getNickname());


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(accessToken, dto.getRefreshToken(), userEntity);
    }

    /**
     * logout
     *
     * @return ResponseEntity<? super DeleteLogoutDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super DeleteLogoutDto> logout(RefreshTokenDto dto) {
        try {
            RefreshToken refreshToken = refreshTokenRepository.findByValue(dto.getRefreshToken());
            if(refreshToken == null) return DeleteLogoutDto.notAuthorization();
            refreshTokenRepository.delete(refreshToken);
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return DeleteLogoutDto.success();
    }

    /**
     * signIn
     *
     * @return ResponseEntity<? super SignInResponseDto>
     * @parm dto
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String accessToken = null;
        String refreshToken = null;
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(dto.getEmail());
            if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
                return SignInResponseDto.signInFailed();
            }

            // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
            accessToken = jwtTokenizer.createAccessToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());
            refreshToken = jwtTokenizer.createRefreshToken(userEntity.getUserId(), userEntity.getEmail(), userEntity.getNickname());

            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setValue(refreshToken);
            refreshTokenEntity.setUserId(userEntity.getUserId());
            refreshTokenRepository.save(refreshTokenEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(accessToken, refreshToken, userEntity);

    }



    /**
     * signUp
     *
     * @return ResponseEntity<? super SignUpResponseDto>
     * @parm requestBody
     */
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestBody) {
        try {
            String email = requestBody.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            String password = requestBody.getPassword();
            String encodePassword = passwordEncoder.encode(password);
            requestBody.setPassword(encodePassword);

            UserEntity userEntity = new UserEntity(requestBody);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }


}