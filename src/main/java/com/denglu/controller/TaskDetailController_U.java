package com.denglu.controller;

import com.denglu.entity.TaskDetail;
import com.denglu.pojo.GetDate;
import com.denglu.pojo.Response;
import com.denglu.service.TaskDetailService;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskDetailController_U {


    @Resource
    private TaskDetailService taskDetailService;


    @PutMapping("/updateTaskDetailByTID/{tid}")
    public Response updateTaskDetailByID(@PathVariable String tid,
                                         @RequestBody Map<String,Object> json){


        String updater = (String) json.get("Updater");
        String replyMessage= (String) json.get("ReplyMessage");
        Response response = new Response();
        TaskDetail taskDetail_1 = taskDetailService.findTaskDetailByTId(tid);
           if(taskDetail_1==null){
               response.setMsg("更新失败，查不到数据");
               response.setCode(404);
           }else {

                   String updatedTime = new GetDate().date();
                   if (updater == null || replyMessage == null){
                       response.setCode(400);
                       response.setMsg("添加失败,参数错误");
                       return response;

                   }else {
                       TaskDetail taskDetail = new TaskDetail(tid,replyMessage,updater,updatedTime);
                       response.setCode(taskDetailService.updateTaskDetailByTID(taskDetail));
                       response.setMsg("修改成功");
                   }
           }

        return response;
    }
}
