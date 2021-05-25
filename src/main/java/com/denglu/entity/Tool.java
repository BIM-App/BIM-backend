package com.denglu.entity;

import lombok.Data;

@Data
public class Tool {
    private String mid;
    private String position;

    public Tool() {
    }

    public Tool(String mid, String position) {
        this.mid = mid;
        this.position = position;
    }
}
