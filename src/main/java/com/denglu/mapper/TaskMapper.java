package com.denglu.mapper;

import com.denglu.entity.Task;

import com.denglu.entity.Tool;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    //查找
    Task findTaskByTID(String tid);
    List<Task> findTaskByPID(String pid);
    List<Task> findTaskByMID(String mid);
    String findUserByUID(String uid);
    String findUIDByUName(String username);
    Task findTaskByMidAndPosition(Tool tool);

    //添加
    int addTask(Task task);

    //删除
    int deleteTaskByTID(String tid);
    int deleteTaskByPID(String pid);

    //更新
    int updateTaskByTID(Task task);

    //绑定模型
    int updateMID(@Param("mId") String mid,@Param("tId") String tid);

    //修改任务状态
    int updateTaskStatus(@Param("taskStatus")String taskStatus,@Param("tId")String tid);

    //添加三维标签
    int updatePosition(@Param("tId") String tid,@Param("position") String position,@Param("information") String information);
}
