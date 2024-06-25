package com.sparta.delivery_app.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
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

        String extension = S3Utils.getValidateImageExtension(file.getOriginalFilename()); // 파일 확장자 검사
        String imageDir = S3Utils.createMenuImageDir(storeId, menuId); // 파일 저장 경로 생성
        deleteFileFromS3(imageDir); // 해당 경로에 파일 존재하면 삭제

        String uploadFileName = imageDir + S3Utils.createFileName(extension);
        return uploadFileToS3(file, uploadFileName); // 실제 S3에 업로드
    }

    public String saveReviewImage(MultipartFile file, Long userId, Long reviewId) {
        if (!S3Utils.isFileExists(file)) {
            return DEFAULT_MESSAGE;
        }

        String extension = S3Utils.getValidateImageExtension(file.getOriginalFilename());
        String imageDir = S3Utils.createMenuImageDir(userId, reviewId);

        deleteFileFromS3(imageDir);

        String uploadFileName = imageDir + S3Utils.createFileName(extension);
        return uploadFileToS3(file, uploadFileName);
    }

    private String uploadFileToS3(MultipartFile file, String uploadFileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, uploadFileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("이미지 업로드 중 오류가 발생했습니다.", e);
            throw new S3Exception(S3ErrorCode.IMAGE_STREAM_ERROR);
        }
        return amazonS3Client.getUrl(bucket, uploadFileName).toString();
    }

    public void deleteFileFromS3(String imageDir) {
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, imageDir);

        if (objectListing.getObjectSummaries().isEmpty()) {
            log.info("파일이 존재하지 않습니다.");
            return;
        }

        while(true) {
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, summary.getKey()));
                log.info("삭제 : " + summary.getKey());
            }

            if(objectListing.isTruncated()) {
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }

}
