package com.denglu.controller.bimface;


import com.bimface.api.bean.request.translate.FileTranslateRequest;
import com.bimface.api.bean.request.translate.TranslateSource;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class Bimface {



    // 初始化BimfaceClient
    protected static BimfaceClient bimfaceClient    = new BimfaceClient("tA87ZM6f7InSm0NMK0TtwYqgQkthynqL", "CBz0mgEar0LHMOZSETIsGRCLvyUgMHqt");

    @PostMapping("/bimFace")
    public void bimFace(Part model, HttpServletRequest request) throws IOException {
        System.out.println(model.getSize());
        System.out.println(model.getSubmittedFileName());
        System.out.println(model.getInputStream());
        //上传文件
        FileBean fileBean =null;
        try {
            FileUploadRequest fileUploadRequest = new FileUploadRequest();

            fileUploadRequest.setContentLength(model.getSize());
            fileUploadRequest.setName(model.getSubmittedFileName());
            fileUploadRequest.setInputStream(model.getInputStream());

            fileBean = bimfaceClient.upload(fileUploadRequest);
        } catch (BimfaceException e) {
        }

        // 获取fileId
        Long fileId = fileBean.getFileId();
        Map<String,String> map = new HashMap<>();
        TranslateSource translateSource = new TranslateSource();
        translateSource.setFileId(fileId);
//        translateSource.setCompressed(false);
//        translateSource.setRootName(model.getSubmittedFileName());
        FileTranslateRequest fileTranslateRequest = new FileTranslateRequest();
        fileTranslateRequest.setSource(translateSource);
        map.put("exportDrawing","true");
        fileTranslateRequest.setConfig(map);
//        fileTranslateRequest.setCallback("https://api.glodon.com/viewing/callback?authCode=iklJk0affae&signature=2ef131395fb6442eb99abd83d45c3201");
        // 发起文件转换
        FileTranslateBean translateBean = null;
        try {
            translateBean = bimfaceClient.translate(fileTranslateRequest);
        } catch (BimfaceException e) {

        }

        Gson gson = new Gson();
        System.out.println("fileId:"+fileId);
        System.out.println(gson.toJson(translateBean));

//        //调用BIMFACE-SDK获取文件转换状态
//        FileTranslateBean translateBean01 = null;
//        try {
//            translateBean01 = bimfaceClient.getTranslate(Long.valueOf(fileId));
//        } catch (NumberFormatException e) {
//        } catch (BimfaceException e) {
//            e.printStackTrace();
//        }
//        System.out.println(gson.toJson(translateBean01));

        // 获取view token
        String viewToken = null;
        try {
            viewToken = bimfaceClient.getViewTokenByFileId(Long.valueOf(fileId));
        } catch (NumberFormatException e) {

        } catch (BimfaceException e) {

        }
        System.out.println("viewToken:"+viewToken);
    }

}
