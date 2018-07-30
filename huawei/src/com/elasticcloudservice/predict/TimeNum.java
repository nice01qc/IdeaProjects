package com.elasticcloudservice.predict;


import java.util.Date;

// 用于存放classifyOneByFlavor 赛选后的数据
class TimeNum {
    public String time;
    public int num;
    public Date date;

    public TimeNum(String time, int num, Date date) {
        this.time = time;
        this.num = num;
        this.date = date;
    }

    public int getTime() {
        return Integer.parseInt(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return time + '\t' + num;
    }
}