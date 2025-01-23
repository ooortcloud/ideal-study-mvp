package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.helper.WebUrlHelper;
import com.idealstudy.mvp.util.RandomValueGenerator;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
@Log4j2
public class S3ProfileRepository {

    private final S3Template s3Template;

    private final WebUrlHelper webUrlHelper;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.s3.profile-path}")
    private String profilePath;

    @Autowired
    public S3ProfileRepository(S3Template s3Template, WebUrlHelper webUrlHelper) {
        this.s3Template = s3Template;
        this.webUrlHelper = webUrlHelper;
    }

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String savePath = profilePath + RandomValueGenerator.createRandomValue() + "_" + fileName;
        S3Resource s3Resource = s3Template.upload(bucketName, savePath, file.getInputStream());

        String profileUri = s3Resource.getURI().toString();
        // log.info("저장된 경로: " + profileUri);

        return webUrlHelper.getKey(profileUri);
    }

    public void deleteFile(String key) {

        // log.info("이미지 제거 작업 수행");
        // log.info("입력받은 key: " + key);
        s3Template.deleteObject(bucketName, key);
    }
}
