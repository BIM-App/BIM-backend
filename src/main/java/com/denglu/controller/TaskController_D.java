package com.denglu.controller;

import com.denglu.entity.Task;
import com.denglu.pojo.Response;
import com.denglu.service.TaskDetailService;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskController_D {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskDetailService taskDetailService;

    @DeleteMapping("/deleteTaskByTID/{tid}")
    public Response deleteTaskByTID(@PathVariable String tid){
        Response response = new Response();
        Task task = taskService.findTaskByTID(tid);

        if (task==null){
            response.setMsg("删除失败,没有查到");
            response.setCode(404);
        }else {

           taskService.deleteTaskByTID(tid);
           taskDetailService.deleteTaskDetailByTID(tid);
           response.setCode(201);
            response.setMsg("删除成功");
        }

        return response;
    }

    @DeleteMapping("/deleteTaskByPID/{pid}")
    public Response deleteTaskByPID(@PathVariable String pid){
        Response response = new Response();
        List<Task> listbytask = taskService.findTaskByPID(pid);
        if(listbytask.size()>0){
            response.setCode(taskService.deleteTaskByPID(pid));
            response.setMsg("删除成功");

        }
        else {
            response.setMsg("删除失败,没有查到");
            response.setCode(404);
        }
        return response;
    }

}
