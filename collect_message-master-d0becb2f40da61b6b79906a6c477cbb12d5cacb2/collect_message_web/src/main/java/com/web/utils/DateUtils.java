package com.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    public static String getDate() {

        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取String类型的时间
        String createdate = sdf.format(date);

        return createdate;
    }

    public static String getTime() {

        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);

        return createdate;
    }


    public static void main(String[] args) {
        System.out.println(getDate());
        System.out.println(getTime());
    }

} 
