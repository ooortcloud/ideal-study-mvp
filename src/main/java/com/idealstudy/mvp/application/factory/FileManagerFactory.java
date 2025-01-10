package com.idealstudy.mvp.application.factory;

import com.idealstudy.mvp.application.service.domain_service.FileManager;
import org.springframework.stereotype.Component;

@Component
public class FileManagerFactory {

    public FileManager getFileManager(String uploadPath) {
        return new FileManager(uploadPath);
    }
}
