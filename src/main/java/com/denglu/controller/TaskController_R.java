package com.denglu.controller;

import com.alibaba.fastjson.JSON;
import com.denglu.entity.Photos;
import com.denglu.entity.Task;
import com.denglu.entity.TaskDetail;
import com.denglu.entity.Tool;
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
public class TaskController_R {

    @Resource
    private TaskService taskService;

    @Resource
   private TaskDetailService taskDetailService;

    String photoURL = null;

    private static String PhotoLocation = "/photo/";

    public Object printf(List<Task> listbytask,HttpServletRequest request) {

        Map<String,Object> map = new HashMap<>();
        List<Map> listbyjson = new ArrayList<>();
        if (listbytask.size() == 0) {
            map.put("code", 404);
            map.put("msg","查无数据");
            map.put("data",listbyjson);
            return map;
        } else {
            for (int i = 0; i < listbytask.size(); i++) {
                Map<String,Object> jsonObject = new HashMap<>();
                String tid = listbytask.get(i).getTId();
                System.out.println("tid"+tid);
                updataTaskStatus(tid);
                String pid = listbytask.get(i).getPId();
                String mid = listbytask.get(i).getMId();
                String tName =listbytask.get(i).getTName();
                String description = listbytask.get(i).getDescription();
                String position = listbytask.get(i).getPosition();
                String information = listbytask.get(i).getInformation();
                String taskStatus =listbytask.get(i).getTaskStatus();

                TaskDetail taskDetail = listbytask.get(i).getTaskDetail();

                String id = taskDetail.getId();
                String publisher = taskDetail.getPublisher();
                String receiver = taskDetail.getReceiver();
                String publisherURL = null;
                String receiverURL = null;
                String replyMessage = taskDetail.getReplyMessage();
                String creator = taskService.findUserByUID(taskDetail.getCreator());
                String createdTime = taskDetail.getCreatedTime();
                String updater = taskService.findUserByUID(taskDetail.getUpdater());
                String updatedTime = taskDetail.getUpdatedTime();



                Map<String, Object> jsonObject1 = new HashMap<>();//总数据
                List<String> list_publisherURL_1 = new ArrayList<>();//发布者上传的图片路径
                List<String> list_receiverURL_1 = new ArrayList<>();//指派人上传图片的路径
                Map<String, Object> map_2 = new HashMap<>();


                if(taskDetail==null){
                    map_2.put("code",404);
                    map_2.put("msg","数据不存在");
                    map_2.put("data",jsonObject1);
                    return map_2;
                }else {

                    if(null == taskDetail.getPhoto()){

                    }else {

                        String pic = taskDetail.getPhoto();
                        Photos p = new Photos();
                        if (pic != null){
                            p = JSON.parseObject(pic, Photos.class);
                            System.out.println("P"+p);
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

                    jsonObject1.put("ID",id);
                    jsonObject1.put("TID",tid);
                    jsonObject1.put("Publisher",map_p);
                    jsonObject1.put("Receiver",map_r);
                    jsonObject1.put("Creator",creator);
                    jsonObject1.put("CreatedTime",createdTime);
                    jsonObject1.put("Updater",updater);
                    jsonObject1.put("UpdatedTime",updatedTime);
                    map_2.put("data",jsonObject1);
                }











                if ("1".equals(taskStatus)){
                    taskStatus = "已发布";
                }else if("2".equals(taskStatus)){
                    taskStatus = "进行中";
                }else if("3".equals(taskStatus)){
                    taskStatus = "已完成";
                }
                String creator1 = listbytask.get(i).getCreator();
                String createdTime1 = listbytask.get(i).getCreatedTime();
                String updater1 = listbytask.get(i).getUpdater();
                String updatedTime1 = listbytask.get(i).getUpdatedTime();
                Map<String,Object> map1 = new HashMap<>();
                if(position==null||("".equals(position))){

                }else {

                    position = position.substring(1,position.length()-1);
                    String s[] = position.split(",");
                    Double x = Double.parseDouble(s[0]);
                    Double y = Double.parseDouble(s[1]);
                    Double z = Double.parseDouble(s[2]);
                    map1.put("x",x);
                    map1.put("y",y);
                    map1.put("z",z);
                }
                jsonObject.put("TID",tid);
                jsonObject.put("PID",pid);
                jsonObject.put("MID",mid);
                jsonObject.put("TName",tName);
                jsonObject.put("description",description);
                jsonObject.put("position",map1);
                jsonObject.put("Information",information);
                jsonObject.put("TaskStatus",taskStatus);
                jsonObject.put("Creator",taskService.findUserByUID(creator1));
                jsonObject.put("CreatedTime",createdTime1);
                jsonObject.put("Updater",taskService.findUserByUID(updater1));
                jsonObject.put("UpdatedTime",updatedTime1);
                jsonObject.put("TaskDetail",map_2);
                System.out.println(jsonObject);
                listbyjson.add(jsonObject);
            }
            map.put("data",listbyjson);
            map.put("msg","success");
            map.put("code",200);
        }

        return map;
    }

    @GetMapping("/findTaskByTID/{tid}")
    public Map<String,Object> findTaskByTID(@PathVariable String tid,HttpServletRequest request){
        updataTaskStatus(tid);
        Map<String,Object> jsonObject = new HashMap<>();
        Map<String,Object> map1 = new HashMap<>();
        Task task = taskService.findTaskByTID(tid);
        if(task!=null){
            String pid = task.getPId();
            String mid = task.getMId();
            String tName = task.getTName();
            String description = task.getDescription();
            String position = task.getPosition();
            String information = task.getInformation();
            String taskStatus =task.getTaskStatus();
            if ("1".equals(taskStatus)){
                taskStatus = "已发布";
            }else if("2".equals(taskStatus)){
                taskStatus = "进行中";
            }else if("3".equals(taskStatus)){
                taskStatus = "已完成";
            }
            String creator = task.getCreator();
            String createdTime = task.getCreatedTime();
            String updater = task.getUpdater();
            String updatedTime = task.getUpdatedTime();
            Map<String,Object> map = new HashMap<>();
            if(position==null||("".equals(position))){

            }else {

                position = position.substring(1,position.length()-1);
                String s[] = position.split(",");
                Double x = Double.parseDouble(s[0]);
                Double y = Double.parseDouble(s[1]);
                Double z = Double.parseDouble(s[2]);
                map.put("x",x);
                map.put("y",y);
                map.put("z",z);
            }
            jsonObject.put("TID",tid);
            jsonObject.put("PID",pid);
            jsonObject.put("MID",mid);
            jsonObject.put("TName",tName);
            jsonObject.put("Description",description);
            jsonObject.put("Position",map);
            jsonObject.put("Information",information);
            jsonObject.put("TaskStatus",taskStatus);
            jsonObject.put("TaskDetail",findTaskDetailByTId(tid,request));
            jsonObject.put("Creator",taskService.findUserByUID(creator));
            jsonObject.put("CreatedTime",createdTime);
            jsonObject.put("Updater",taskService.findUserByUID(updater));
            jsonObject.put("UpdatedTime",updatedTime);

            map1.put("code",200);
            map1.put("msg","success");
            map1.put("data",jsonObject);
        }else {

            map1.put("code",404);
            map1.put("msg","查无此数据");
            map1.put("data",jsonObject);
        }
        return map1;

    }
    @GetMapping("/findTaskByPID/{pid}")
    public Object findTaskByPID(@PathVariable String pid, HttpServletRequest request){
        List<Task> listbytask = taskService.findTaskByPID(pid);
        printf(listbytask,request);
        List<Task> listbytask_1 = taskService.findTaskByPID(pid);
        return printf(listbytask_1,request);
    }
    @GetMapping("/findTaskByMID/{mid}")
    public Object findTaskByMID(@PathVariable String mid, HttpServletRequest request){
        List<Task> listbytask = taskService.findTaskByMID(mid);
        printf(listbytask,request);
        List<Task> listbytask_1 = taskService.findTaskByMID(mid);
        return printf(listbytask_1,request);
    }

    public void updataTaskStatus(String tid){

        TaskDetail taskDetail = taskDetailService.findTaskDetailByTId(tid);

        if(taskDetail != null){
            String msg = taskDetail.getReplyMessage();
            String user = taskDetail.getReceiver();
            if(user !=null){
                taskService.updateTaskStatus("2",tid);
            }
            if(msg !=null){
                taskService.updateTaskStatus("3",tid);
            }
            if(user == null && msg == null){
                taskService.updateTaskStatus("1",tid);
            }
        }
    }

    @GetMapping("/findTaskByMidAndPosition")
    public Object findTaskByMidAndPosition(String mid,Double x,Double y,Double z,HttpServletRequest request) {

        String position = "(" + x + "," + y + "," + z + ")";
        Tool tool = new Tool(mid, position);
        //查询任务
        Task task = taskService.findTaskByMidAndPosition(tool);
        //查出任务的ID
        String tid = task.getTId();
        //接收任务详情信息
        TaskDetail taskDetail = task.getTaskDetail();
        System.out.println(task);
        String id = taskDetail.getId();
        String publisher = taskDetail.getPublisher();
        String receiver = taskDetail.getReceiver();
        String publisherURL = null;
        String receiverURL = null;
        String replyMessage = taskDetail.getReplyMessage();
        String creator = taskService.findUserByUID(taskDetail.getCreator());
        String createdTime = taskDetail.getCreatedTime();
        String updater = taskService.findUserByUID(taskDetail.getUpdater());
        String updatedTime = taskDetail.getUpdatedTime();



        Map<String, Object> jsonObject = new HashMap<>();//总数据
        List<String> list_publisherURL_1 = new ArrayList<>();//发布者上传的图片路径
        List<String> list_receiverURL_1 = new ArrayList<>();//指派人上传图片的路径
        Map<String, Object> map_task = new HashMap<>();

        if(null == taskDetail.getPhoto()){

        }else {

            String pic = taskDetail.getPhoto();
            Photos p = new Photos();
            if (pic != null){
                p = JSON.parseObject(pic, Photos.class);
                System.out.println("P"+p);
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
        jsonObject.put("TID",tid);
        jsonObject.put("Publisher",map_p);
        jsonObject.put("Receiver",map_r);
        jsonObject.put("Creator",creator);
        jsonObject.put("CreatedTime",createdTime);
        jsonObject.put("Updater",updater);
        jsonObject.put("UpdatedTime",updatedTime);
        map_task.put("data",jsonObject);

        updataTaskStatus(tid);
        Map<String, Object> jsonObject1 = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        if (task != null) {
            String pid = task.getPId();
            String tName = task.getTName();
            String description = task.getDescription();
            String information = task.getInformation();
            String taskStatus = task.getTaskStatus();
            if ("1".equals(taskStatus)) {
                taskStatus = "已发布";
            } else if ("2".equals(taskStatus)) {
                taskStatus = "进行中";
            } else if ("3".equals(taskStatus)) {
                taskStatus = "已完成";
            }
            String creator_task = task.getCreator();
            String createdTime_task = task.getCreatedTime();
            String updater_task = task.getUpdater();
            String updatedTime_task = task.getUpdatedTime();
            Map<String, Object> map = new HashMap<>();
            map.put("x", x);
            map.put("y", y);
            map.put("z", z);
            jsonObject1.put("TID", tid);
            jsonObject1.put("PID", pid);
            jsonObject1.put("MID", mid);
            jsonObject1.put("TName", tName);
            jsonObject1.put("Description", description);
            jsonObject1.put("Position", map);
            jsonObject1.put("Information", information);
            jsonObject1.put("TaskStatus", taskStatus);
            jsonObject1.put("TaskDetail",map_task);
            jsonObject1.put("Creator", taskService.findUserByUID(creator_task));
            jsonObject1.put("CreatedTime", createdTime_task);
            jsonObject1.put("Updater", taskService.findUserByUID(updater_task));
            jsonObject1.put("UpdatedTime", updatedTime_task);
            map1.put("code", 200);
            map1.put("msg", "success");
            map1.put("data", jsonObject1);
        }
        else {

            map1.put("code",404);
            map1.put("msg","任务不存在");
            map1.put("data",jsonObject1);
        }

        return map1;
    }

    public Map<String, Object> findTaskDetailByTId(String tid, HttpServletRequest request){

        TaskDetail taskDetail = taskDetailService.findTaskDetailByTId(tid);
        System.out.println(taskDetail);

        String id = taskDetail.getId();
        String tName = taskService.findTaskByTID(tid).getTName();
        String publisher = taskDetail.getPublisher();
        String receiver = taskDetail.getReceiver();
        String publisherURL = null;
        String receiverURL = null;
        String replyMessage = taskDetail.getReplyMessage();
        String creator = taskService.findUserByUID(taskDetail.getCreator());
        String createdTime = taskDetail.getCreatedTime();
        String updater = taskService.findUserByUID(taskDetail.getUpdater());
        String updatedTime = taskDetail.getUpdatedTime();



        Map<String, Object> jsonObject = new HashMap<>();//总数据
        List<String> list_publisherURL_1 = new ArrayList<>();//发布者上传的图片路径
        List<String> list_receiverURL_1 = new ArrayList<>();//指派人上传图片的路径
        Map<String, Object> map = new HashMap<>();

            if(null == taskDetail.getPhoto()){

            }else {

                String pic = taskDetail.getPhoto();
                Photos p = new Photos();
                if (pic != null){
                    p = JSON.parseObject(pic, Photos.class);
                    System.out.println("P"+p);
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
            jsonObject.put("TID",tid);
            jsonObject.put("TName",tName);
            jsonObject.put("Publisher",map_p);
            jsonObject.put("Receiver",map_r);
            jsonObject.put("Creator",creator);
            jsonObject.put("CreatedTime",createdTime);
            jsonObject.put("Updater",updater);
            jsonObject.put("UpdatedTime",updatedTime);
            map.put("data",jsonObject);

        return map;
    }
}
