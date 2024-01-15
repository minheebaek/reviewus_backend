package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.backend.dto.response.ResponseDto;
import com.example.backend.dto.response.user.PutUserInfoImageResponseDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ImageService {
    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /**
     * uploadImage
     *
     * @param userId
     * @param multipartFile
     * @param dirName
     * @return ResponseEntity<? super PutUserInfoImageResponseDto>
     */
    public ResponseEntity<? super PutUserInfoImageResponseDto> uploadImage(Long userId, MultipartFile multipartFile, String dirName) { //  multipartFile을 전달받아 file로 전환한 후 s3에 업로드
        UserEntity userEntity = null;
        String profileImage = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return PutUserInfoImageResponseDto.notExistUser();
            System.out.println(multipartFile+"multipartFile");
            if (!multipartFile.isEmpty()) {
                File uploadFile = convert(multipartFile); //파일 변환할 수 없으면 에러
                if (uploadFile == null) return ResponseDto.databaseError();
                profileImage = upload(uploadFile, dirName);
            }
            userEntity.updateUserImage(profileImage);
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutUserInfoImageResponseDto.success();
    }

    /**
     * upload
     *
     * @param uploadFile
     * @param dirName
     * @return String
     */
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "." + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile); //로컬에 생성된 File 삭제(MultipartFile -> File로 전환하여 로컬에 파일 생성됨)

        return uploadImageUrl; // 업로드된 파일의 S3 URL 주소 반환
    }


    /**
     * putS3
     *
     * @param uploadFile
     * @param fileName
     * @return String
     */
    private String putS3(File uploadFile, String fileName) { //s3 버킷에 이미지 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead) //publicRead 권한으로 업로드
        );
        return amazonS3Client.getUrl(bucket, fileName)
                .toString();
    }

    /**
     * removeNewFile
     *
     * @param targetFile
     */
    private void removeNewFile(File targetFile) { //로컬에 있는 이미지 삭제
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    /**
     * convert
     *
     * @param file
     * @return File
     * @throws IOException
     */
    private File convert(MultipartFile file) throws IOException { //로컬에 파일 업로드하기
        // 해당 객체는 프로그램이 실행되는 로컬 디렉토리(루트 디렉토리)에 위치하게 됨
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        // File convertFile = new File(file.getOriginalFilename());

        if (convertFile.createNewFile()) { // 위에 지정한 경로에 file이 생성됨(경로가 잘못되어있다면 생성불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { //FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            if (convertFile != null) return convertFile;
        }
        return null;

    }


    /**
     * getThumbnailPath
     *
     * @param path
     * @return String
     */
    public String getThumbnailPath(String path) { // find image from s3
        return amazonS3Client.getUrl(bucket, path).toString();
    }

    /**
     * deleteFile
     *
     * @param fileName
     */
    public void deleteFile(String fileName) {//remove s3 object
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }
}