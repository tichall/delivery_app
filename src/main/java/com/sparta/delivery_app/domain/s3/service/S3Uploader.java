package com.sparta.delivery_app.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.delivery_app.common.exception.errorcode.AwsS3ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.FileUploadFailedException;
import com.sparta.delivery_app.domain.s3.util.S3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 메뉴 이미지 저장 경로 store/{storeId}/menu/{menuId}
    private static final String STORE_DIR = "store";
    private static final String MENU_IMAGE_DIR = "menu";

    // 리뷰 이미지 저장 경로 user/{userId}/review/{reviewId}
    private static final String USER_DIR = "user";
    private static final String REVIEW_IMAGE_DIR = "review";

    private String DEFAULT_MESSAGE = "이미지를 등록하지 않았습니다.";


    public String saveMenuImage(MultipartFile file, Long storeId, Long menuId) throws IOException {

        if (!isFileExists(file)) {
            return DEFAULT_MESSAGE;
        }
        String fileName = S3Utils.createFileName(
                file,
                STORE_DIR,
                storeId,
                MENU_IMAGE_DIR,
                menuId
        );

        return uploadFileToAws(file, fileName);
    }

    public String saveReviewImage(MultipartFile file, Long userId, Long reviewId) throws IOException {

        if (!isFileExists(file)) {
            return DEFAULT_MESSAGE;
        }
        String fileName = S3Utils.createFileName(
                file,
                USER_DIR,
                userId,
                REVIEW_IMAGE_DIR,
                reviewId
        );

        return uploadFileToAws(file, fileName);
    }

    private String uploadFileToAws(MultipartFile multipartFile, String fileName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 업로드 중 오류가 발생했습니다.", e);
            throw new FileUploadFailedException(AwsS3ErrorCode.IMAGE_UPLOAD_ERROR);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

//    public void deleteImageFile(String imagePathUrl) {
//        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, imagePathUrl));
//    }

    private boolean isFileExists(MultipartFile multipartFile) {
        return !multipartFile.isEmpty();
    }
}
