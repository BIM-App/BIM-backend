package com.denglu.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

@Data
public class Task {

    private String tId;
    private String mId;
    private String pId;
    private String tName;
    private String description;
    private String position;
    private String information;
    private String creator;
    private String updater;
    private String createdTime;
    private String updatedTime;
    private String taskStatus;
    private TaskDetail taskDetail;

    @Override
    public String toString() {
        return JSON.toJSONString(this, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.UseISO8601DateFormat });
    }

    public Task() {
    }

    public Task(String pId, String tName, String description, String creator, String updater, String createdTime, String updatedTime) {
        this.pId = pId;
        this.tName = tName;
        this.description = description;
        this.creator = creator;
        this.updater = updater;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Task( String tId, String tName, String description,String updater, String updatedTime) {
        this.tId=tId;
        this.tName = tName;
        this.description = description;
        this.updater = updater;
        this.updatedTime = updatedTime;
    }
}
