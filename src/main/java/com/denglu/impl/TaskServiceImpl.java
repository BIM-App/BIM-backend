package com.denglu.impl;

import com.denglu.entity.Task;
import com.denglu.entity.Tool;
import com.denglu.mapper.TaskMapper;
import com.denglu.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;


    @Override
    public Task findTaskByTID(String tid) {
        return taskMapper.findTaskByTID(tid);
    }


    @Override
    public List<Task> findTaskByPID(String pid) {
        return taskMapper.findTaskByPID(pid);
    }

    @Override
    public List<Task> findTaskByMID(String mid) {
        return taskMapper.findTaskByMID(mid);
    }

    @Override
    public String findUserByUID(String uid) {
        return taskMapper.findUserByUID(uid);
    }

    @Override
    public String findUIDByUName(String username) {
        return taskMapper.findUIDByUName(username);
    }

    @Override
    public Task findTaskByMidAndPosition(Tool tool) {
        return taskMapper.findTaskByMidAndPosition(tool);
    }


    @Override
    public int addTask(Task task) {
        taskMapper.addTask(task);
        return 200;
    }

    @Override
    public int deleteTaskByTID(String tid) {
        taskMapper.deleteTaskByTID(tid);
        return 200;
    }

    @Override
    public int deleteTaskByPID(String pid) {
        taskMapper.deleteTaskByPID(pid);
        return 200;
    }

    @Override
    public int updateTaskByTID(Task task) {

        taskMapper.updateTaskByTID(task);
        return 200;
    }

    @Override
    public int updateMID(String mid,String tid) {
        taskMapper.updateMID(mid,tid);
        return 200;
    }

    @Override
    public int updateTaskStatus(String taskStatus, String tid) {
        if(taskMapper.updateTaskStatus(taskStatus,tid)>0){
            return 200;
        }
        else {
            return 500;
        }
    }

    @Override
    public int updatePosition(String tid, String position, String information) {
        if(taskMapper.updatePosition(tid,position,information)>0){
            return 200;
        }
        else {
            return 500;
        }
    }

}
