package com.elasticcloudservice.predict;

import com.filetool.util.FileUtil;

import java.io.File;
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
        results = new String[inputNodes.length+60];
        for (int i = 1; i< 30; i++){
            String aim = "flavor"+i;
            classifyAllByFlavor(inputNodes,results,aim);
        }
        FileUtil.write("C:\\Users\\newbie\\Desktop\\sdk-java\\code\\ecs\\src\\mydata.txt",results,false);

        System.out.println("Minute: " + inputNodes[0].getMinute());
        System.out.println("Hour:" + inputNodes[0].getHour());
        System.out.println("Day:" + inputNodes[0].getDay());


        //以上用于测试
        return results;
    }

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

    }

    // 用于输出一条数据，便于MATLAB调试
    public static void classifyAllByFlavor(InputNode[] inputNodes,String[] results, String aim) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < inputNodes.length; i++) {
            InputNode x = inputNodes[i];
            if (!x.ecsFlavor.equals(aim)) continue;
            String key = x.getHour()+"";                 // 更改时间初
            if (hashMap.containsKey(key)) {
                hashMap.put(key, hashMap.get(key) + 1);
            } else {
                hashMap.put(key, 1);
            }
        }
        int begin = 0;
        for (int i = 0; i < results.length; i++){
            if (results[i]!=null) begin++;
            else break;
        }
        results[begin] = aim.replaceAll("flavor","") + "\t" + "0";
        int i = begin;
        ArrayList<String> list = new ArrayList<>(hashMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            results[++i] = key + "\t" + hashMap.get(key);
//            System.out.println(key + "\t" + hashMap.get(key));
        }
    }

    //
    public static void classifyOneByFlavor(InputNode[] inputNodes,List<String> results, String aim) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < inputNodes.length; i++) {
            InputNode x = inputNodes[i];
            if (!x.ecsFlavor.equals(aim)) continue;
            String key = x.getMinute()+"";                 // 更改时间初
            if (hashMap.containsKey(key)) {
                hashMap.put(key, hashMap.get(key) + 1);
            } else {
                hashMap.put(key, 1);
            }
        }

        ArrayList<String> list = new ArrayList<>(hashMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            results.add(key + "\t" + hashMap.get(key));
//            System.out.println(key + "\t" + hashMap.get(key));
        }
    }

}
