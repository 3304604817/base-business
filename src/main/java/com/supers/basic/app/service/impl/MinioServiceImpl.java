package com.supers.basic.app.service.impl;

import com.supers.basic.app.service.MinioService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Service
public class MinioServiceImpl implements MinioService {
    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Override
    public void upload(MultipartFile file) throws Exception {
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        InputStream fileStream = file.getInputStream(); //得到文件流
        String fileName = file.getOriginalFilename(); //文件名
        String contentType = file.getContentType();  //类型
        PutObjectOptions putObjectOptions = new PutObjectOptions(fileStream.available(), -1);
        minioClient.putObject(bucketName, fileName, fileStream, putObjectOptions); //把文件放置Minio桶(文件夹)
    }

    @Override
    public void uploadBase64(String fileName, String base64File) throws Exception {
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        // Base64字符转文件流
        byte[] byteFile = Base64.getDecoder().decode(base64File);
        InputStream fileStream = new ByteArrayInputStream(byteFile);
        PutObjectOptions putObjectOptions = new PutObjectOptions(fileStream.available(), -1);
        minioClient.putObject(bucketName, fileName, fileStream, putObjectOptions); //把文件放置Minio桶(文件夹)
    }

    @Override
    public InputStream download(HttpServletResponse response, String fileName) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/force-download"); // response 设置问文件下载
        response.setCharacterEncoding("UTF-8");
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        return minioClient.getObject(bucketName, fileName);
    }

    @Override
    public String getFileUrl(String fileName) throws Exception {
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        return minioClient.presignedGetObject(bucketName, fileName);
    }

    @Override
    public void removeFile(String fileName) throws Exception{
        MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
        minioClient.removeObject(bucketName, fileName);
    }
}
