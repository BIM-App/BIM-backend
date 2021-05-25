package com.denglu.controller;

import com.denglu.entity.TaskDetail;
import com.denglu.pojo.GetDate;
import com.denglu.pojo.Response;
import com.denglu.entity.Task;
import com.denglu.service.TaskDetailService;
import com.denglu.service.TaskService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static springfox.documentation.swagger2.mappers.SerializableParameterFactories.factory;


@RestController
@CrossOrigin(allowCredentials = "true")
public class TaskController_C {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskDetailService taskDetailService;

    @PostMapping("/addTask")
    public Response addTask(@RequestBody Map<String,Object> json){

        String pid = (String) json.get("Pid");
        String tName = (String) json.get("TName");
        String description = (String) json.get("Description");
        String creator = (String) json.get("Creator");
        String receiver =(String) json.get("Receiver");
        String updater =creator;
        String createdTime = new GetDate().date();
        String updatedTime = createdTime;
        Response response = new Response();

        if (taskService.findUIDByUName(receiver)!=null||receiver == null){
            //添加任务
            Task task = new Task(pid,tName,description,creator,updater,createdTime,updatedTime);

            //获取新建任务的主键
            taskService.addTask(task);
            String tid = task.getTId();

            String publisher = taskService.findUserByUID(creator);

            //自动插入任务详情
            taskDetailService.addTaskDetail(new TaskDetail(tid,publisher,receiver,creator,createdTime,updater,updatedTime));

            response.setCode(200);
            response.setMsg("添加成功");
        }else {
            response.setCode(404);
            response.setMsg("添加失败，指派人不存在");
        }
        return response;
    }

}
