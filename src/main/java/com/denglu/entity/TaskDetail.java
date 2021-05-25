package com.denglu.entity;


import lombok.Data;

@Data
public class TaskDetail {
    private String id;
    private String tid;
    private String publisher;
    private String receiver;
    private String photo;
    private String replyMessage;
    private String creator;
    private String createdTime;
    private String updater;
    private String updatedTime;
    private  Task task;

    public TaskDetail() {
    }

    public TaskDetail(String tid, String publisher,String receiver,String creator, String createdTime, String updater, String updatedTime) {
        this.tid = tid;
        this.publisher = publisher;
        this.receiver = receiver;
        this.creator = creator;
        this.createdTime = createdTime;
        this.updater = updater;
        this.updatedTime = updatedTime;
    }

    public TaskDetail(String tid, String replyMessage, String updater, String updatedTime) {
        this.tid = tid;
        this.replyMessage = replyMessage;

        this.updater = updater;
        this.updatedTime = updatedTime;
    }
}
