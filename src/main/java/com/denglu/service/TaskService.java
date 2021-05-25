package com.denglu.service;

import com.denglu.entity.Task;
import com.denglu.entity.Tool;

import java.util.List;

public interface TaskService {

    Task findTaskByTID(String tid);
    List<Task> findTaskByPID(String pid);
    List<Task> findTaskByMID(String mid);
    String findUserByUID(String uid);
    String findUIDByUName(String username);
    Task findTaskByMidAndPosition(Tool tool);

    int addTask(Task task);

    int deleteTaskByTID(String tid);
    int deleteTaskByPID(String pid);

    int updateTaskByTID(Task task);

    int updateMID(String mid,String tid);

    int updateTaskStatus(String TaskStatus,String tid);

    int updatePosition(String tid,String position,String information);

}
