package com.wanda.ffanad.crm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.DateFormatter;

import com.wanda.ffanad.common.constants.Constant;

/**
 * 日期工具
 * 
 * @Title : DateUtil.java
 * @author yanji
 * @date 2016年6月13日 16:28
 */
public class DateUtil {
    /**
     * sdf
     */
    public static SimpleDateFormat YYYY_MM_DD_SDF = new SimpleDateFormat(Constant.DateFormat.YYYY_MM_DD);
    
    /**
     * 格式化yyyyMM字符日期
     * 
     * @return Date
     */
    public static Date transferDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date;
        try {
            date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }
    /**
     * 获取年份
     * 
     * @return year
     */
    public static String getYear(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date;
        try {
            date = sdf.parse(dateString);
            String time = getDate(date.getTime());
            String year = time.substring(0, 4);
            return year;
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 获取月份
     * 
     * @return month
     */
    public static String getMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date;
        try {
            date = sdf.parse(dateString);
            String time = getDate(date.getTime());
            //   String year = time.substring(0, 4);
            String month = Integer.valueOf(time.substring(4, 6)).toString();
            return month;
        } catch (ParseException e) {
            return "";
        }
    }

    public static String getDate(long datetime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(datetime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//格式化为年月
        return sdf.format(c.getTime());
    }
    
    /**
     * 毫秒转换为日期
     * 
     * @return yyyymmdd
     */
    public static String getFormatDate(String datetime) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        long time = Long.valueOf(datetime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }
    
    /**
     * 获取现在到明天凌晨的时间间隔（秒）
     * @return
     * @throws ParseException 
     */
    public static Long getNowToTomorrowTimeStamp() throws ParseException{
        Date nowD=new Date();
        long now=nowD.getTime();
        Date date = YYYY_MM_DD_SDF.parse(YYYY_MM_DD_SDF.format(nowD));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return (cal.getTimeInMillis()-now)/1000;
    }
}
