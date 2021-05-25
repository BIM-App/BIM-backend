package com.denglu.controller;

import com.alibaba.fastjson.JSON;
import com.denglu.entity.Photos;
import com.denglu.entity.Task;
import com.denglu.entity.TaskDetail;
import com.denglu.service.TaskDetailService;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskDetailController_R {

    String photoURL = null;

    private static String PhotoLocation = "/photo/";

    @Resource
    private TaskDetailService taskDetailService;

    @Resource
    private TaskService taskService;

    @GetMapping("/findTaskDetailByReceiver/{receiver}")
    public Object findTaskDetailByReceiver(@PathVariable String receiver,HttpServletRequest request){
        //全部数据
        Map<String,Object> mapTaskList = new HashMap<>();

        //整个task加上taskDetail的数据
        List<Map> listJson = new ArrayList<>();

        List<TaskDetail> listReceiver = taskDetailService.findTaskDetailByReceiver(receiver);

        if(taskService.findUIDByUName(receiver) != null){
            for (int i = 0;i<listReceiver.size();i++){
                //task的数据
                Map<String,Object> mapTask = new HashMap<>();
                Task task = listReceiver.get(i).getTask();
                String tid = task.getTId();
                String pid = task.getPId();
                String mid = task.getMId();
                String tName =task.getTName();
                String description = task.getDescription();
                String position = task.getPosition();
                String information = task.getInformation();
                String taskStatus =task.getTaskStatus();
                Map<String,Object> map1 = new HashMap<>();
                if(position==null||("".equals(position))){

                }else {
                    position = position.substring(1, position.length() - 1);
                    String s[] = position.split(",");
                    Double x = Double.parseDouble(s[0]);
                    Double y = Double.parseDouble(s[1]);
                    Double z = Double.parseDouble(s[2]);

                    map1.put("x",x);
                    map1.put("y",y);
                    map1.put("z",z);
                }

                String creator_1 = taskService.findUserByUID(task.getCreator());
                String createdTime_1 = task.getCreatedTime();
                String updater_1 = taskService.findUserByUID(task.getUpdater());
                String updatedTime_1 = task.getUpdatedTime();
                mapTask.put("TID",tid);
                mapTask.put("PID",pid);
                mapTask.put("MID",mid);
                mapTask.put("TName",tName);
                mapTask.put("description",description);
                mapTask.put("position",map1);
                mapTask.put("Information",information);
                mapTask.put("TaskStatus",taskStatus);
                mapTask.put("Creator",creator_1);
                mapTask.put("CreatedTime",createdTime_1);
                mapTask.put("Updater",updater_1);
                mapTask.put("UpdatedTime",updatedTime_1);


                String id = listReceiver.get(i).getId();
                String publisher = listReceiver.get(i).getPublisher();
                String publisherURL = null;
                String receiverURL = null;
                String replyMessage = listReceiver.get(i).getReplyMessage();
                String creator = taskService.findUserByUID(listReceiver.get(i).getCreator());
                String createdTime = listReceiver.get(i).getCreatedTime();
                String updater = taskService.findUserByUID(listReceiver.get(i).getUpdater());
                String updatedTime = listReceiver.get(i).getUpdatedTime();

                List<String> list_publisherURL_1 = new ArrayList<>();//发布者上传的图片路径
                List<String> list_receiverURL_1 = new ArrayList<>();//指派人上传图片的路径

                Map<String,Object> jsonObject = new HashMap<>();
                if(null == listReceiver.get(i).getPhoto()){

                }else {

                    String pic = listReceiver.get(i).getPhoto();
                    Photos p = new Photos();
                    if (pic != null){
                        p = JSON.parseObject(pic, Photos.class);
                    }

                    publisherURL = p.getPublisher();

                    receiverURL = p.getReceiver();

                    photoURL = request.getScheme() + "://" + request.getServerName()
                            + ":" + request.getServerPort() + "/Task" + PhotoLocation;
                    if (null != publisherURL){
                        List<String> list_publisherURL = Arrays.asList(publisherURL.split(","));
                        for (int j = 0; j < list_publisherURL.size(); j++) {
                            list_publisherURL_1.add(photoURL + list_publisherURL.get(j));

                        }
                    }
                    if(null !=  receiverURL){
                        List<String> list_receiverURL = Arrays.asList(receiverURL.split(","));
                        for (int j = 0; j < list_receiverURL.size(); j++) {
                            list_receiverURL_1.add(photoURL + list_receiverURL.get(j));
                        }
                    }


                }

                Map<String,Object> map_r = new HashMap<>();//指派人信息
                Map<String,Object> map_p = new HashMap<>();//发布人信息

                map_p.put("Name",publisher);
                map_p.put("Photo",list_publisherURL_1);

                map_r.put("Name",receiver);
                map_r.put("Photo",list_receiverURL_1);
                map_r.put("ReplyMessage",replyMessage);

                jsonObject.put("ID",id);
                jsonObject.put("Publisher",map_p);
                jsonObject.put("Receiver",map_r);
                jsonObject.put("Creator",creator);
                jsonObject.put("CreatedTime",createdTime);
                jsonObject.put("Updater",updater);
                jsonObject.put("UpdatedTime",updatedTime);

                mapTask.put("TaskDetail",jsonObject);
                listJson.add(mapTask);
            }
            mapTaskList.put("code",200);
            mapTaskList.put("msg","success");
            mapTaskList.put("data",listJson);
        }else {
            mapTaskList.put("code",404);
            mapTaskList.put("msg","指派人不存在");
            mapTaskList.put("data",listJson);
            return mapTaskList;
        }

        return mapTaskList;
    }

}
