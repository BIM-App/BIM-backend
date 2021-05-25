package com.denglu.entity;


public class Photos {

    private String publisher;
    private String receiver;

    public Photos(String publisher, String receiver) {
        this.publisher = publisher;
        this.receiver = receiver;

    }

    public Photos() {
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"publisher\":\"")
                .append(publisher).append('\"');
        sb.append(",\"receiver\":\"")
                .append(receiver).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
