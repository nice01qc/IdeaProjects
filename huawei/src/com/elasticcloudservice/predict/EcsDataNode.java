package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class EcsDataNode {
    public String ecsID;
    public String ecsFlavor;
    public String time;

    public EcsDataNode(String ecsID, String ecsFlavor, String time) {
        this.ecsID = ecsID;
        this.ecsFlavor = ecsFlavor;
        this.time = time;
    }


    public Date getDate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date) f.parseObject(this.time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private long getMinute() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60;
    }

    private long getHour() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60;
    }

    private long getDay() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24;
    }

    private long getDay2() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 2;
    }

    private long getDay3() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 3;
    }

    private long getDay4() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 4;
    }

    private long getDay5() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 5;
    }

    private long getDay6() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 6;
    }

    private long getWeek() {
        Date date = getDate();
        if (date == null) return 0;
        return date.getTime() / 1000 / 60 / 60 / 24 / 7;
    }

    // 通过time type 获取具体那种数据
    public long getDateByTimeType(TimeType timeType){
        long result = 0;
        switch (timeType){
            case MINUTE:
                result = getMinute();
                break;
            case HOUR:
                result = getHour();
                break;
            case DAY:
                result = getDay();
                break;
            case DAY2:
                result = getDay2();
                break;
            case DAY3:
                result = getDay3();
                break;
            case DAY4:
                result = getDay4();
                break;
            case DAY5:
                result = getDay5();
                break;
            case DAY6:
                result = getDay6();
                break;
            case WEEK:
                result = getWeek();
                break;
            default:
                return result;
        }
        return result;
    }


    @Override
    public String toString() {
        return "InputNode{" + "ecsID='" + ecsID + '\'' + ", ecsFlavor='" + ecsFlavor + '\'' + ", time='" + time + '\'' + '}';
    }

}