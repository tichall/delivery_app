package com.sparta.delivery_app.domain.s3.util;

import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3Utils {

    public static String createFileName(MultipartFile file, String firstDir, Long firstId, String secondDir, Long secondId) {

        return firstDir + "/"
                + firstId + "/"
                + secondDir + "/"
                + secondId + "/"
                + file.getOriginalFilename() + "_"
                + System.currentTimeMillis();
    }
}
