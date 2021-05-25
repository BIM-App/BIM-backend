package com.denglu.controller;

import com.denglu.pojo.Response;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskBindModel {

    @Resource
    private TaskService taskService;

    @PutMapping("/TaskBindModel/{tid}")
    public Response TaskBindModel(@PathVariable String tid, @RequestBody Map<String,String> json){
        Response response = new Response();
        String mid = json.get("Mid");

       if (mid!=null){
           if(taskService.findTaskByTID(tid)==null){
               response.setMsg("任务不存在，绑定失败");
               response.setCode(404);
           }else {

               response.setCode(taskService.updateMID(mid,tid));
               response.setMsg("绑定成功");
           }
       }else {
           response.setMsg("参数错误，绑定失败");
           response.setCode(400);
       }
        return response;
    }

}
