package com.denglu.service;

import com.denglu.entity.TaskDetail;

import java.util.List;

public interface TaskDetailService {
    List<TaskDetail> findTaskDetailByReceiver(String receiver);
    TaskDetail findTaskDetailByTId(String tid);


    int addTaskDetail(TaskDetail taskDetail);

    int deleteTaskDetailByTID(String tid);

    int updateTaskDetailByTID(TaskDetail taskDetail);

    int updatePhoto(String photo,String id);

    int updateTaskDetailReceiverByTID(String receiver,String tid);
}
