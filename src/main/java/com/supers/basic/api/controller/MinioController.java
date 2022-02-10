package com.supers.basic.api.controller;

import com.supers.basic.app.service.MinioService;
import com.supers.common.util.jwt.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

@Api(tags="minio文件服务")
@RestController
@RequestMapping("/minio")
public class MinioController {
    @Autowired
    private MinioService minioService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public ResponseEntity<String> upload(@ApiParam(value="选择文件", required = true) @RequestParam("fileName") MultipartFile file) {
        try {
            minioService.upload(file);
            return new ResponseEntity("上传成功", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("上传失败", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "基于Base64字符串文件上传")
    @PostMapping("upload-base64")
    public ResponseEntity<String> uploadBase64(@ApiParam(value="文件名",required = true) @RequestParam(value = "fileName") String fileName,
                                               @RequestBody(required = true) String base64File) {
        try {
            minioService.uploadBase64(fileName, base64File);
            return new ResponseEntity("上传成功", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("上传失败", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download")
    public ResponseEntity<String> download(HttpServletResponse response, @ApiParam(value="文件名",required = true) @RequestParam(value = "fileName") String fileName) {
        InputStream in = null;
        try {
            in = minioService.download(response, fileName);
            IOUtils.copy(in, response.getOutputStream());
            return new ResponseEntity("下载成功", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("下载失败", HttpStatus.OK);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "获取minio文件下载地址")
    @GetMapping("/file-url")
    public ResponseEntity<String> getFileUrl(@ApiParam(value="文件名",required = true) @RequestParam(value = "fileName") String fileName) {
        try {
            return new ResponseEntity(minioService.getFileUrl(fileName), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("获取失败", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFile(@ApiParam(value="文件名",required = true) @RequestParam(value = "fileName") String fileName) {
        try {
            minioService.removeFile(fileName);
            return new ResponseEntity("删除成功", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("删除失败", HttpStatus.OK);
        }
    }

    @Access(accessNoToken = true)
    @ApiOperation(value = "文件转Base64字符串")
    @PostMapping("file-to-base64")
    public ResponseEntity<String> fileToBase64(@ApiParam(value="选择文件",required=true) @RequestPart("fileName") MultipartFile file) {
        try {
            // MultipartFile转字节流
            byte[] imgBytes = file.getBytes();
            // 文件流转Base64
            String base64Str = Base64.getEncoder().encodeToString(imgBytes);
            return new ResponseEntity(base64Str, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("转换成功", HttpStatus.OK);
        }
    }
}
