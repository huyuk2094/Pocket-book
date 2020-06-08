package com.design.accountingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2019/6/10.
 */

public class DateUtil {

    //unix时间转为正常时间
    public static String getFormattedTime(long timeStamp){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timeStamp));
    }

    //2019-06-01
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    //将日期转为yyyy-MM-dd形式
    private static Date strToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    //返回星期
    public static String getWeekDay(String date){
        String[] weekdays = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(date));
        int index = calendar.get(Calendar.DAY_OF_WEEK)-1;//读date中的星期
        return  weekdays[index];
    }

    //返回月份
    public static String getDateTitle(String date){
        String[] months ={"January", "February", "March", "April", "May", "June","July", "August","September","October", "November", "December"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//读date中的月份
        return  months[monthIndex] + " "+String.valueOf(day);//2019-6-10 —> June 10
    }
}
