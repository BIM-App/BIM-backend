package com.denglu.controller;

import com.denglu.pojo.Response;
import com.denglu.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskBindPosition {

    @Resource
    private TaskService taskService;

    @PutMapping("/TaskBindPosition/{tid}")
    public Response TaskBindModel(@PathVariable String tid, @RequestBody Map<String,Object> json){
        Response response = new Response();
        Map position_small = (HashMap) json.get("Position");
        Double x = (Double) position_small.get("x");
        Double y = (Double) position_small.get("y");
        Double z = (Double) position_small.get("z");
        String position= "("+x+","+y+","+z+")";
        String information = (String) json.get("Information");

       if (position_small!=null){
           if(taskService.findTaskByTID(tid)==null){
               response.setMsg("任务不存在，添加坐标失败");
               response.setCode(404);
           }else {
               System.out.println("if:"+information);
               response.setCode(taskService.updatePosition(tid,position,information));
               response.setMsg("添加坐标成功");
           }
       }else {
           response.setMsg("参数错误，绑定失败");
           response.setCode(400);
       }
        return response;
    }

}
