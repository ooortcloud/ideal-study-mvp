package com.idealstudy.mvp.unit.domain_service;


import com.idealstudy.mvp.application.service.domain_service.FileManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class FileManagerTest {

    private FileManager fileManager;

    public FileManagerTest() {

        String uploadPath = "src/main/resources/static/temp/";
        fileManager = new FileManager(uploadPath);
    }

    @Test
    public void saveGetDelete() throws IOException {

        File tempFile = new File("src/main/resources/static/temp/apple.jpeg");
        InputStream is = new FileInputStream(tempFile);
        String originalFileName = tempFile.getName();

        String savedUri = fileManager.saveFile(is, originalFileName);

        File getFile = fileManager.getFile(savedUri);
        Assertions.assertThat(getFile.getParentFile()).isEqualTo(tempFile.getParentFile());

        fileManager.deleteFile(savedUri);
        Assertions.assertThatThrownBy(() -> fileManager.getFile(savedUri));
    }
}
