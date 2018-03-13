package com.elasticcloudservice.predict;

import com.filetool.util.FileUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Predict {

    public static String[] predictVm(String[] ecsContent, String[] inputContent) {

        /** =========do your work here========== **/
        // 每个flavor 的加速值，用于二次方项
        //                       1   2   3   4  5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20
        double acceleration[] = {0 , 0 , 0 , 0, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 };
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
// -------------------------------------------------------------------------------------------
        InputNode[] inputNodes = new InputNode[inputContent.length];

        getInputNodes(inputNodes, inputContent);

//        for (int i = 0; i < inputContent.length; i++) {
//            inputContent[i] = inputContent[i].replaceAll("[ \t]+", " ");
//            String[] array = inputContent[i].split(" ");
//            InputNode node = new InputNode(array[0], array[1], array[2] + " " + array[3]);
//            inputNodes[i] = node;
//        }

        // 一下用于测试

//        test1(inputNodes);

//        String[] inputContentTwo = FileUtil.read("C:\\Users\\newbie\\IdeaProjects\\sdk-java\\code\\ecs\\src\\inputFilePath2.txt",null);
//        InputNode[] inputNodesTwo = new InputNode[inputContentTwo.length];
//        getInputNodes(inputNodesTwo,inputContentTwo);
//
//
        test2(inputNodes, TimeType.DAY);







        List<TimeNum> list1 = new LinkedList<>();
        classifyOneByFlavor(inputNodes, list1, "flavor8", TimeType.DAY);    // 选出数据 list1
        numCumulation(list1);   // 累加list1的值
        zeroBegin(list1);       // 减去第一个值
        double fitresult[] = new double[2]; // 拟合结果存放

        computeLinearFittingPara(list1, fitresult); // 计算拟合结果

        System.out.println(list1);
        System.out.println(fitresult[0] + "\t" + fitresult[1]);
        System.out.println(reappearProbabilityByDay(inputNodes,list1));



















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

    enum TimeType {MINUTE, HOUR, DAY, DAY3, WEEK}
    // 计算线性回归方程
    static class RegressionLine {
        private double sumX;
        private double sumY;
        private double sumXX;
        private double sumXY;
        private double sumYY;
        private double sumDeltaY2;
        private double sst;
        private double E;
        private String[] xy;
        private ArrayList listX;
        private ArrayList listY;
        private int XMax, YMax;
        private float a0;
        private float a1;
        private int pn;
        private boolean coefsValid;

        public RegressionLine() {
            XMax = 0;
            YMax = 0;
            pn = 0;
            xy = new String[2];
            listX = new ArrayList();
            listY = new ArrayList();
        }

        public float getb() {
            validateCoefficients();
            return a0;
        }

        public float getw() {
            validateCoefficients();
            return a1;
        }

        public void addDataPoint(float x, float y) {
            sumX += x;
            sumY += y;
            sumXX += x * x;
            sumXY += x * y;
            sumYY += y * y;

            if (x > XMax) {
                XMax = (int) x;
            }
            if (y > YMax) {
                YMax = (int) y;
            }

            // 把每个点的具体坐标存入ArrayList中，备用

            xy[0] = (int) x + "";
            xy[1] = (int) y + "";
            if (x != 0 && y != 0) {

                try {
                    listX.add(pn, xy[0]);
                    listY.add(pn, xy[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            ++pn;
            coefsValid = false;
        }

        public float at(int x) {
            if (pn < 2)
                return Float.NaN;

            validateCoefficients();
            return a0 + a1 * x;
        }

        private void validateCoefficients() {
            if (coefsValid)
                return;

            if (pn >= 2) {
                float xBar = (float) sumX / pn;
                float yBar = (float) sumY / pn;

                a1 = (float) ((pn * sumXY - sumX * sumY) / (pn * sumXX - sumX
                        * sumX));
                a0 = (float) (yBar - a1 * xBar);
            } else {
                a0 = a1 = Float.NaN;
            }

            coefsValid = true;
        }

        public double getR() {
            for (int i = 0; i < pn - 1; i++) {
                float Yi = (float) Integer.parseInt(listY.get(i).toString());
                float Y = at(Integer.parseInt(listX.get(i).toString()));
                float deltaY = Yi - Y;
                float deltaY2 = deltaY * deltaY;
                sumDeltaY2 += deltaY2;

            }

            sst = sumYY - (sumY * sumY) / pn;
            E = 1 - sumDeltaY2 / sst;

            return round(E, 4);
        }

        public double round(double v, int scale) {

            if (scale < 0) {
                throw new IllegalArgumentException(
                        "The scale must be a positive integer or zero");
            }

            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

        }

        public float round(float v, int scale) {

            if (scale < 0) {
                throw new IllegalArgumentException(
                        "The scale must be a positive integer or zero");
            }

            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();

        }

//        private static void printLine(RegressionLine line) {
//            System.out.println("\n回归线公式:  y = " + line.getw() + "x + "
//                    + line.getb());
//            System.out.println("误差：     R^2 = " + line.getR());
//        }

    }

    /**
     * 分解和提取原始数据
     *
     * @param inputNodes   把每条数据转换成InputNode存放的地方
     * @param inputContent 存放的原始数据数组
     */
    public static void getInputNodes(InputNode[] inputNodes, String[] inputContent) {
        for (int i = 0; i < inputContent.length; i++) {
            inputContent[i] = inputContent[i].replaceAll("[ \t]+", " ");
            String[] array = inputContent[i].split(" ");
            InputNode node = new InputNode(array[0], array[1], array[2] + " " + array[3]);
            inputNodes[i] = node;
        }
    }

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
            } else if (timeType == TimeType.WEEK) {
                key = x.getWeek() + "";
            } else {
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

    /**
     * 每次通过aim（flavor）和 TimeType，筛选出一组数据
     *
     * @param inputNodes
     * @param results    存放结果的list
     * @param aim        flavor类型
     * @param timeType   时间单位
     */
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
            } else if (timeType == TimeType.WEEK) {
                key = x.getWeek() + "";
            } else {
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

    /**
     * 递增
     *
     * @param list
     */
    public static void numCumulation(List<TimeNum> list) {
        if (list.isEmpty()) return;
        for (int i = 1; i < list.size(); i++) {
            list.get(i).num += list.get(i - 1).num;
        }
    }

    /**
     * 计算线性拟合参数
     *
     * @param xylist 要拟合的数据
     * @param result w,b返回结果存放处
     */
    public static void computeLinearFittingPara(final List<TimeNum> xylist, double[] result) {
        if (xylist.isEmpty()) return;
        double w = 0.1, b = -100, eta = 0.9;
        RegressionLine regressionLine = new RegressionLine();
        for (TimeNum timeNum : xylist) {
            float xtime = Float.valueOf(timeNum.time);
            float ynum = (float) timeNum.num;
            regressionLine.addDataPoint(xtime,ynum);
        }
        result[0] = regressionLine.getw();
        result[1] = regressionLine.getb();
    }

    /**
     * 计算此flavor平均每天出现的概率
     * @param inputNodes
     * @param timeNumLists
     * @return
     */
    public static double reappearProbabilityByDay(final InputNode[] inputNodes, final List<TimeNum> timeNumLists){
        if (inputNodes == null || timeNumLists == null) return 0.0;
        double result = 0.0;
        double inputNodessize = inputNodes[inputNodes.length-1].getDay() - inputNodes[0].getDay();
        result = timeNumLists.size() / inputNodessize;
        System.out.println(timeNumLists.size() + "\t" + inputNodessize);
        return result;
    }

    // 测试1
    public static void test1(InputNode[] inputNodes) {
        String[] results = null;

        results = new String[inputNodes.length + 60];
        for (int i = 1; i < 30; i++) {
            String aim = "flavor" + i;
            classifyAllByFlavor(inputNodes, results, aim, TimeType.DAY);
        }
        FileUtil.write("C:\\Users\\nice01qc\\IdeaProjects\\sdk-java\\code\\ecs\\src\\mydata.txt", results, false);

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
            filterDataAndReplaceByMean(list, 6);
            if (list.isEmpty()) continue;
            resultList.add(String.valueOf(i) + '\t' + "0");
            for (int j = 0; j < list.size(); j++) {
                resultList.add(list.get(j).toString());
            }
        }

        FileUtil.write("C:\\Users\\nice01qc\\IdeaProjects\\sdk-java\\code\\ecs\\src\\mydata.txt", resultList, false);
    }

}
