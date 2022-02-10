package com.supers.basic.app.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public interface MinioService {
    void upload(MultipartFile file) throws Exception;

    void uploadBase64(String fileName, String base64File) throws Exception;

    InputStream download(HttpServletResponse response, String fileName) throws Exception;

    String getFileUrl(String fileName) throws Exception;

    void removeFile(String fileName) throws Exception;
}
