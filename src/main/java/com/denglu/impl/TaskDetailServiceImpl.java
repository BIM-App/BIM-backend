package com.denglu.impl;

import com.denglu.entity.TaskDetail;
import com.denglu.mapper.TaskDetailMapper;
import com.denglu.service.TaskDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TaskDetailServiceImpl implements TaskDetailService {

    @Resource
    private TaskDetailMapper taskDetailMapper;

    @Override
    public  List<TaskDetail> findTaskDetailByReceiver(String receiver) {
        return taskDetailMapper.findTaskDetailByReceiver(receiver);
    }

    @Override
    public TaskDetail findTaskDetailByTId(String tid) {
        return taskDetailMapper.findTaskDetailByTId(tid);
    }

    @Override
    public int addTaskDetail(TaskDetail taskDetail) {
        taskDetailMapper.addTaskDetail(taskDetail);
        return 201;
    }


    @Override
    public int deleteTaskDetailByTID(String tid) {
        taskDetailMapper.deleteTaskDetailByTID(tid);
        return 204;
    }

    @Override
    public int updateTaskDetailByTID(TaskDetail taskDetail) {
        taskDetailMapper.updateTaskDetailByTID(taskDetail);
        return 200;
    }

    @Override
    public int updatePhoto(String photo,String tid) {
        if(taskDetailMapper.updatePhoto(photo,tid)>0){
            return 200;
        }
       return 0;
    }

    @Override
    public int updateTaskDetailReceiverByTID(String receiver,String tid) {
        return taskDetailMapper.updateTaskDetailReceiverByTID(receiver,tid);
    }
}
