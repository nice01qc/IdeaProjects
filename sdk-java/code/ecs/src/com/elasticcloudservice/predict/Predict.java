package com.elasticcloudservice.predict;

import com.filetool.util.FileUtil;

import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Predict {

    public static String[] predictVm(String[] ecsContent, String[] inputContent) {

        /** =========do your work here========== **/

        String[] results = null;

        List<String> history = new ArrayList<String>();

        for (int i = 1; i < ecsContent.length; i++) {

            if (ecsContent[i].contains(" ")
                    && ecsContent[i].split(" ").length == 3) {

                String[] array = ecsContent[i].split(" ");
                String uuid = array[0];
                String flavorName = array[1];
                String createTime = array[2];
                history.add(uuid + " " + flavorName + " " + createTime);
            }
        }

        InputNode[] inputNodes = new InputNode[inputContent.length];
        for (int i = 0; i < inputContent.length; i++) {
            inputContent[i] = inputContent[i].replaceAll("[ \t]+", " ");
            String[] array = inputContent[i].split(" ");
            InputNode node = new InputNode(array[0], array[1], array[2] + " " + array[3]);
            inputNodes[i] = node;
        }

        // 一下用于测试

//        test1(inputNodes);
        test2(inputNodes, TimeType.DAY3);
//        List<TimeNum> list = new LinkedList<>();
//        classifyOneByFlavor(inputNodes,list,"flavor3",TimeType.MINUTE);
//        zeroBegin(list);
//        System.out.println(variance(list));
//        System.out.println(newVariance(list));
//        filterData(list,2);
//        System.out.println(list);

//
//        System.out.println("Minute: " + inputNodes[0].getMinute());
//        System.out.println("Hour:" + inputNodes[0].getHour());
//        System.out.println("Day:" + inputNodes[0].getDay());
//        System.out.println("Day3:" + inputNodes[0].getDay3());

        //以上用于测试
        return results;
    }

    // 用于存放每条数据具体信息
    static class InputNode {
        String ecsID;
        String ecsFlavor;
        String time;

        public InputNode(String ecsID, String ecsFlavor, String time) {
            this.ecsID = ecsID;
            this.ecsFlavor = ecsFlavor;
            this.time = time;
        }

        public String getEcsID() {
            return ecsID;
        }

        public void setEcsID(String ecsID) {
            this.ecsID = ecsID;
        }

        public String getEcsFlavor() {
            return ecsFlavor;
        }

        public void setEcsFlavor(String ecsFlavor) {
            this.ecsFlavor = ecsFlavor;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "InputNode{" + "ecsID='" + ecsID + '\'' + ", ecsFlavor='" + ecsFlavor + '\'' + ", time='" + time + '\'' + '}';
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

        public long getMinute() {
            Date date = getDate();
            if (date == null) return 0;
            return date.getTime() / 1000 / 60;
        }

        public long getHour() {
            Date date = getDate();
            if (date == null) return 0;
            return date.getTime() / 1000 / 60 / 60;
        }

        public long getDay() {
            Date date = getDate();
            if (date == null) return 0;
            return date.getTime() / 1000 / 60 / 60 / 24;
        }

        public long getDay3() {
            Date date = getDate();
            if (date == null) return 0;
            return date.getTime() / 1000 / 60 / 60 / 24 / 3;
        }

        public long getWeek() {
            Date date = getDate();
            if (date == null) return 0;
            return date.getTime() / 1000 / 60 / 60 / 24 / 7;
        }

    }

    // 用于存放classifyOneByFlavor 赛选后的数据
    static class TimeNum {
        String time;
        int num;

        public TimeNum(String time, int num) {
            this.time = time;
            this.num = num;
        }

        public String getTime() {
            return time;
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

    static enum TimeType {MINUTE, HOUR, DAY, DAY3, WEEK}

    // 用于输出一条数据，便于MATLAB调试
    public static void classifyAllByFlavor(final InputNode[] inputNodes, String[] results, String aim, TimeType timeType) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < inputNodes.length; i++) {
            InputNode x = inputNodes[i];
            if (!x.ecsFlavor.equals(aim)) continue;
            String key = null;
            if (timeType == TimeType.DAY) {              // 更改时间初
                key = x.getDay() + "";
            } else if (timeType == TimeType.HOUR) {
                key = x.getHour() + "";
            } else if (timeType == TimeType.MINUTE) {
                key = x.getMinute() + "";
            } else if(timeType == TimeType.WEEK){
                key = x.getWeek() + "";
            }else{
                key = x.getDay3() + "";
            }
            if (hashMap.containsKey(key)) {
                hashMap.put(key, hashMap.get(key) + 1);
            } else {
                hashMap.put(key, 1);
            }
        }
        int begin = 0;
        for (int i = 0; i < results.length; i++) {
            if (results[i] != null) begin++;
            else break;
        }
        results[begin] = aim.replaceAll("flavor", "") + "\t" + "0";
        int i = begin;
        ArrayList<String> list = new ArrayList<>(hashMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            results[++i] = key + "\t" + hashMap.get(key);
//            System.out.println(key + "\t" + hashMap.get(key));
        }
    }

    // 每次通过aim（flavor）和 TimeType，筛选出一组数据
    public static void classifyOneByFlavor(final InputNode[] inputNodes, List<TimeNum> results, String aim, TimeType timeType) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < inputNodes.length; i++) {
            InputNode x = inputNodes[i];
            if (!x.ecsFlavor.equals(aim)) continue;
            String key = null;
            if (timeType == TimeType.DAY) {              // 更改时间初
                key = x.getDay() + "";
            } else if (timeType == TimeType.HOUR) {
                key = x.getHour() + "";
            } else if (timeType == TimeType.MINUTE) {
                key = x.getMinute() + "";
            } else if(timeType == TimeType.WEEK){
                key = x.getWeek() + "";
            }else{
                key = x.getDay3() + "";
            }

            if (hashMap.containsKey(key)) {
                hashMap.put(key, hashMap.get(key) + 1);
            } else {
                hashMap.put(key, 1);
            }
        }

        ArrayList<String> list = new ArrayList<>(hashMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            TimeNum timeNum = new TimeNum(key, hashMap.get(key));
            results.add(timeNum);
        }
    }

    // 使每个值都减去 第一个值，与归一化相似
    public static void zeroBegin(List<TimeNum> resultlist) {
        if (resultlist.isEmpty()) return;
        int begin = Integer.valueOf(resultlist.get(0).time) - 1;
        for (TimeNum x : resultlist) {
            x.time = String.valueOf(Integer.valueOf(x.time) - begin);
        }
    }

    // 计算给定一组数据 resultList 的方差
    public static double variance(final List<TimeNum> resultlist) {
        if (resultlist.size() <= 0) return -1;
        double result = 0.0;
        double num1 = resultlist.get(0).num;
        double time1 = Double.valueOf(resultlist.get(0).time);
        for (int i = 1; i < resultlist.size(); i++) {
            double num2 = resultlist.get(i).num;
            double time2 = Double.valueOf(resultlist.get(i).time);
            result += Math.pow(num2 - num1, 2) + Math.pow(time2 - time1, 2);
            num1 = num2;
            time1 = time2;
        }
        result /= resultlist.size();
        return result;
    }

    // 计算给定一组数据 resultList 的标准差
    public static double standardDeviation(final List<TimeNum> resultlist) {
        if (resultlist.isEmpty()) return -1;
        double result = variance(resultlist);
        result = Math.sqrt(result);
        return result;
    }

    // 计算给定一组数据 resultList 新的方差-> 每组数据都开平方根
    public static double newVariance(final List<TimeNum> resultlist) {
        if (resultlist.size() <= 0) return -1;
        double result = 0.0;
        double num1 = resultlist.get(0).num;
        double time1 = Double.valueOf(resultlist.get(0).time);
        for (int i = 1; i < resultlist.size(); i++) {
            double num2 = resultlist.get(i).num;
            double time2 = Double.valueOf(resultlist.get(i).time);
            result += Math.sqrt(Math.pow(num2 - num1, 2) + Math.pow(time2 - time1, 2));
            num1 = num2;
            time1 = time2;
        }
        result /= resultlist.size();
        return result;
    }

    // 计算给定一组数据平均值,返回一个TimeNum 的平均值
    public static TimeNum mean(final List<TimeNum> resultlist) {
        if (resultlist.size() <= 0) return null;
        TimeNum result = new TimeNum("", 0);
        double num = 0.0;
        double time = 0.0;
        for (int i = 0; i < resultlist.size(); i++) {
            double num1 = resultlist.get(i).num;
            double time1 = Double.valueOf(resultlist.get(i).time);
            num += num1;
            time += time1;
        }
        result.setNum(Integer.valueOf((int) (num / resultlist.size())));
        result.setTime(String.valueOf(time / resultlist.size()));
        return result;
    }

    // 给定阀值，过滤数据，直接删除
    public static void filterData(List<TimeNum> resultlist, double threshold) {
        if (resultlist.isEmpty()) return;
        for (int i = 0; i < resultlist.size(); i++) {
            if (resultlist.get(i).getNum() > threshold) {
                resultlist.remove(i);
                i--;
            }
        }
    }

    // 给定阀值，过滤数据，替换为平均值
    public static void filterDataAndReplaceByMean(List<TimeNum> resultlist, double threshold) {
        if (resultlist.isEmpty()) return;
        TimeNum timeNumMean = mean(resultlist);
        for (int i = 0; i < resultlist.size(); i++) {
            if (resultlist.get(i).getNum() > threshold) {
                resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num));
            }
        }
    }


    // 测试1
    public static void test1(InputNode[] inputNodes) {
        String[] results = null;

        results = new String[inputNodes.length + 60];
        for (int i = 1; i < 30; i++) {
            String aim = "flavor" + i;
            classifyAllByFlavor(inputNodes, results, aim, TimeType.DAY);
        }
        FileUtil.write("C:\\Users\\newbie\\IdeaProjects\\sdk-java\\code\\ecs\\src\\mydata.txt", results, false);

        System.out.println("Minute: " + inputNodes[0].getMinute());
        System.out.println("Hour:" + inputNodes[0].getHour());
        System.out.println("Day:" + inputNodes[0].getDay());
    }

    // 改进型 测试2
    public static void test2(InputNode[] inputNodes, TimeType timeType) {
        List<String> resultList = new LinkedList<>();
        for (int i = 1; i < 30; i++) {
            List<TimeNum> list = new LinkedList<>();
            String aim = "flavor" + i;
            classifyOneByFlavor(inputNodes, list, aim, timeType);
            // 归一化
            zeroBegin(list);
            // 过滤数据处理
            filterDataAndReplaceByMean(list,10);
            if (list.isEmpty()) continue;
            resultList.add(String.valueOf(i) + '\t' + "0");
            for (int j = 0; j < list.size(); j++) {
                resultList.add(list.get(j).toString());
            }
        }

        FileUtil.write("C:\\Users\\newbie\\IdeaProjects\\sdk-java\\code\\ecs\\src\\mydata.txt", resultList, false);
    }


}
