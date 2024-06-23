package com.sparta.delivery_app.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.delivery_app.common.exception.errorcode.S3ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.S3Exception;
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

    private String DEFAULT_MESSAGE = "이미지를 등록하지 않았습니다.";


    public String saveMenuImage(MultipartFile file, Long storeId, Long menuId) {
        if (!S3Utils.isFileExists(file)) {
            return DEFAULT_MESSAGE;
        }

        S3Utils.validateImageExtension(file.getOriginalFilename());

        String imageDir = S3Utils.createMenuImageDir(storeId, menuId);
        return uploadFileToS3(file, imageDir);
    }

    public String saveReviewImage(MultipartFile file, Long userId, Long reviewId) {
        if (!S3Utils.isFileExists(file)) {
            return DEFAULT_MESSAGE;
        }

        S3Utils.validateImageExtension(file.getOriginalFilename());

        String imageDir = S3Utils.createReviewImageDir(userId, reviewId);
        return uploadFileToS3(file, imageDir);
    }

    private String uploadFileToS3(MultipartFile file, String imageDir) {
        String fileName = imageDir + file.getOriginalFilename() + "_"
                + System.currentTimeMillis();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 업로드 중 오류가 발생했습니다.", e);
            throw new S3Exception(S3ErrorCode.IMAGE_STREAM_ERROR);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteFileFromS3(String imagePathUrl) {
        String s3Url = S3Utils.extractImagePath(imagePathUrl);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, s3Url));
    }

}
