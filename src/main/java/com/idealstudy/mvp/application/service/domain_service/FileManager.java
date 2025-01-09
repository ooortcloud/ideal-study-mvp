package com.idealstudy.mvp.application.service.domain_service;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.UUID;

@Slf4j
public class FileManager {

    private final String UPLOAD_PATH;

    public FileManager(String uploadPath) {

        UPLOAD_PATH = uploadPath;
    }

    public String saveFile(InputStream inputStream, String originalFileName) throws IOException {

        /*
            모든 파일명을 랜덤하게 한 뒤, 해당 파일명의 실제 이름을 따로 DB에 일대일 매핑해두는 식으로 저장하면
            파일 원본명을 지킴과 동시에 보안적으로도 안전함.
         */

        String uuid = UUID.randomUUID().toString();
        String filePath = UPLOAD_PATH + uuid + "_" + originalFileName;

        try (OutputStream outputStream = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[8192]; // 8KB 버퍼 크기 설정

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.");
        }

        return filePath;
    }

    public File getFile(String uri) throws RuntimeException {

        File file = new File(uri);
        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }
        return file;
    }

    public void deleteFile(String uri) throws FileSystemException {

        File file = getFile(uri);

        if ( !file.delete()) {
            log.error("파일 제거 실패");
            throw new FileSystemException("파일 제거 실패");
        }
    }
}