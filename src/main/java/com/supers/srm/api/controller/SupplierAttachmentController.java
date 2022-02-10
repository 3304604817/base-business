package com.supers.srm.api.controller;

import com.supers.common.util.jwt.annotation.Access;
import com.supers.srm.domain.entity.CompanyBasic;
import com.supers.srm.infra.mapper.CompanyBasicMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Api(tags="SupplierAttachment")
@RestController
@RequestMapping("/supplier-attachment")
public class SupplierAttachmentController {

    @Autowired
    private CompanyBasicMapper companyBasicMapper;


    @Access(accessNoToken = true)
    @ApiOperation(value = "刷供应商")
    @GetMapping("/update-companyBasic")
    public ResponseEntity<?> updateSplmCompanyBasic() {
        List<CompanyBasic> companyBasics = companyBasicMapper.selectCompanyBasic();
        for(CompanyBasic companyBasic:companyBasics){
            companyBasicMapper.updateSplmSupplierBasic(companyBasic.getFileUrl(), companyBasic.getCompanyName());
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    @Access(accessNoToken = true)
    @ApiOperation(value = "上传附件")
    @GetMapping("/upload-file")
    public ResponseEntity<?> uploadFile() {
        try {
            String filepath = "/Users/gaoyang/Downloads/用友系统供应商附件汇总版20211020";
            File file = new File(filepath);  //File类型可以是文件也可以是文件夹
            File[] fileArray = file.listFiles();   //将该目录下的所有文件放置在一个File类型的数组中

            List<File> folderList = new ArrayList<File>();  //新建一个文件夹集合
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i].isDirectory()) {  //判断是否为文件夹
                    folderList .add(fileArray[i]);
                }
            }

            for(File folder:folderList){
                File[] subFileArray = folder.listFiles();

                List<File> subFileList = new ArrayList<File>();  //新建一个文件集合
                for (int i = 0; i < subFileArray.length; i++) {
                    if (subFileArray[i].isFile()) {  //判断是否为文件
                        subFileList.add(subFileArray[i]);
                    }
                }

                for(File subFile:subFileList){
                    if(subFile.getName().contains("营业执照")){
                        String fileUrl = this.saveFile(subFile);
                        companyBasicMapper.insertCompanyBasic(folder.getName(), fileUrl);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    public String saveFile(File file){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file",file.getName(),
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    file))
                    .build();
            Request request = new Request.Builder()
                    .url("https://srm.mapfarm.com/hfle/v1/0/files/multipart?bucketName=private-bucket&directory=spfm-comp")
//                    .url("http://srmuat.mapfarm.com/hfle/v1/0/files/multipart?bucketName=private-bucket&directory=hsdr01")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer 38eca26b-76de-4564-9a40-dd91aa8b8a36")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
