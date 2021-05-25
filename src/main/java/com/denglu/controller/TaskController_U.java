package com.denglu.controller;

import com.denglu.entity.Task;
import com.denglu.pojo.GetDate;
import com.denglu.pojo.Response;
import com.denglu.service.TaskDetailService;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskController_U {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskDetailService taskDetailService;


    @PutMapping("/updateTaskByTID/{tid}")
    public Response updateTaskByTID (@PathVariable String tid,@RequestBody Map<String,Object> json){
        String tName = (String) json.get("TName");
        String description = (String) json.get("Description");
        String receiver =(String) json.get("Receiver");
        String updater = (String) json.get("Updater");
        String updatedTime = new GetDate().date();
        Response response = new Response();
        if(taskService.findTaskByTID(tid)!=null){
           if(taskService.findUIDByUName(receiver)!=null){
               taskService.updateTaskByTID(new Task(tid,tName,description,updater,updatedTime));
               taskDetailService.updateTaskDetailReceiverByTID(receiver,tid);
               response.setCode(200);
               response.setMsg("修改成功");
           }else {
               response.setMsg("修改失败,指派人不存在");
               response.setCode(404);
           }
        }else {
            response.setMsg("修改失败,任务编号不存在");
            response.setCode(404);
        }
        return response;

    }
}
