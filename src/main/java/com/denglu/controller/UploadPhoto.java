package com.denglu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.denglu.entity.Photos;
import com.denglu.pojo.Response;
import com.denglu.service.TaskDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

@RestController
@CrossOrigin(allowCredentials = "true")
public class UploadPhoto {


    @Resource
    private TaskDetailService taskDetailService;

    private static String PhotoLocation = "/photo/";


    @PutMapping("/uploadPhoto/{tid}")
    public Response uploadPhoto(Part[] photo, @PathVariable String tid, String identity, HttpServletRequest request){

        String pic = taskDetailService.findTaskDetailByTId(tid).getPhoto();
        Photos p = new Photos();
        if (pic != null){
           p = JSON.parseObject(pic, Photos.class);
        }


        Response response = new Response();
        String path = request.getSession().getServletContext().getRealPath(PhotoLocation);//此处为tomcat下的路径，服务重启路径会变化

        String picture = null;
        for (int i = 0; i < photo.length; i++) {
            String filename = photo[i].getSubmittedFileName();
            picture += "," + filename;
            try {
                photo[i].write(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        picture = picture.substring(5);

        String json = null;
        if("Publisher".equals(identity)){
            p.setPublisher(picture);
            json = JSON.toJSONString(p); // 序列化
            System.out.println("json"+json);
        }
        if("Receiver".equals(identity)){
            p.setReceiver(picture);
            json = JSON.toJSONString(p); // 序列化
        }


        response.setMsg("上传图片成功");
        response.setCode(taskDetailService.updatePhoto(json,tid));

        return response;
    }
}
