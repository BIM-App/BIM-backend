package com.denglu.mapper;

import com.denglu.entity.TaskDetail;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import javax.print.DocFlavor;
import java.util.List;

@Mapper
public interface TaskDetailMapper {
    //查找
    List<TaskDetail> findTaskDetailByReceiver(String receiver);
    TaskDetail findTaskDetailByTId(String tid);

    //添加
    int addTaskDetail(TaskDetail taskDetail);

    int deleteTaskDetailByID(String id);
    int deleteTaskDetailByTID(String tid);

    int updateTaskDetailByTID(TaskDetail taskDetail);

    int updatePhoto(@Param("photo")String photo, @Param("tid")String tid);

    int updateTaskDetailReceiverByTID(@Param("receiver")String receiver,@Param("tid") String tid);
}
