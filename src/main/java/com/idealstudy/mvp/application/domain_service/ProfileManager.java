package com.idealstudy.mvp.application.domain_service;

import com.idealstudy.mvp.infrastructure.S3ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileManager {

    private final S3ProfileRepository profileRepository;

    @Autowired
    public ProfileManager(S3ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String upload(MultipartFile file) throws IOException {
        return profileRepository.uploadFile(file);
    }

    public void delete(String key) {
        profileRepository.deleteFile(key);
    }
}
