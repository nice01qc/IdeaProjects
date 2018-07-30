package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 存取比赛要求和相关指标
 */
class InputeContent {
    public int serverCpuNum;
    public int serverMemSize;
    public int serverHDSize;
    public List<Flavor> flavorList;
    public String ourAim;
    public String begin;
    public String end;

    public InputeContent() {
    }

    public Date getDate(String time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date) f.parseObject(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public long getBeginByWeek() {
        Date date = getDate(begin);
        return date.getTime() / 1000 / 60 / 60 / 24 / 7;
    }

    public long getBeginByDay() {
        Date date = getDate(begin);
        return date.getTime() / 1000 / 60 / 60 / 24;
    }

    public long getBeginByHour() {
        Date date = getDate(begin);
        return date.getTime() / 1000 / 60 / 60;
    }

    public long getEndByWeek() {
        Date date = getDate(end);
        return date.getTime() / 1000 / 60 / 60 / 24 / 7;
    }

    public long getEndByDay() {
        Date date = getDate(end);
        return date.getTime() / 1000 / 60 / 60 / 24;
    }

    public long getEndByHour() {
        Date date = getDate(end);
        return date.getTime() / 1000 / 60 / 60;
    }

    public long getBegin(TimeType timeType) {
        switch (timeType) {
            case WEEK:
                return getBeginByWeek();
            case DAY:
                return getBeginByDay();
            case HOUR:
                return getBeginByHour();
            default:
                return 0;
        }
    }

    public long getEnd(TimeType timeType) {
        switch (timeType) {
            case WEEK:
                return getEndByWeek();
            case DAY:
                return getEndByDay();
            case HOUR:
                return getEndByHour();
            default:
                return 0;
        }
    }
}
