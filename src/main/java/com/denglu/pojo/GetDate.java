package com.denglu.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    public String date(){
        Date date=new Date();
        DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String punchTime = simpleDateFormat.format(date);
        return punchTime;
    }
}
