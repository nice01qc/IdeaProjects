package com.elasticcloudservice.predict;

import java.util.*;

public class FilterData {

    // 分类一个数据并且加一些处理
    public static void clssifyOneByFlavorAndFilter(EcsDataNode[] ecsDataNodes, List<TimeNum> results, String aim, TimeType timeType, int filterKind, boolean accumulation, int interpolation) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<String, Date> hashMapTime = new HashMap<>();
        for (int i = 0; i < ecsDataNodes.length; i++) {
            EcsDataNode x = ecsDataNodes[i];
            if (!x.ecsFlavor.equals(aim)) continue;
            String key = x.getDateByTimeType(timeType) + "";

            if (hashMap.containsKey(key)) {
                hashMap.put(key, hashMap.get(key) + 1);
            } else {
                hashMap.put(key, 1);
                hashMapTime.put(key, x.getDate());
            }
        }
        if (hashMap.isEmpty()) return; // 么有元素，直接返回
        ArrayList<String> list = new ArrayList<String>(hashMap.keySet());
        Collections.sort(list);

        for (String key : list) {
            TimeNum timeNum = new TimeNum(key, hashMap.get(key), hashMapTime.get(key));
            results.add(timeNum);
        }

        // 处理一下啊数据
        Mymethod.zeroBegin(results, timeType);      // 归一化，归一化


        if (filterKind == 1) {         // 过滤数据判断
            filterDataBystandardDeviation(results, 3);
        } else if (filterKind == 2) {
            filterDataAndReplaceBySubsection3(results, timeType);
        }else if (filterKind == 3) {
            filterDataByAngle(results);
        }
        // 无累加插值
        if (interpolation == 2){
            interpolationBySide(results);
        }

        if (accumulation) {
            numCumulation(results);   // 累加list1的值
        }
        // 插值
        if (interpolation == 1) {
            interpolation(results);
        }
    }


    /*-------------------------------- 一些过滤数据的方法 -------------------------------*/

    // 给平均值的波动范围，过滤数据，替换为平均值,三倍均值加2
    public static void filterDataAndReplaceByMeanRange(List<TimeNum> resultlist, double range) {
        if (resultlist.isEmpty()) return;
        TimeNum timeNumMean = mean(resultlist);
        double threshold = Double.valueOf(timeNumMean.num) * range + 2;
        for (int i = 0; i < resultlist.size(); i++) {
            if (resultlist.get(i).getNum() > threshold) {
                resultlist.set(i, new TimeNum(resultlist.get(i).time, (int) threshold, resultlist.get(i).date));
            }
        }

    }

    // 给平均值的波动范围，过滤数据，替换为平均值,默认它已经归一化
    public static void filterDataAndReplaceBySubsection3(List<TimeNum> resultlist, TimeType timeType) {
        if (resultlist.isEmpty()) return;
        TimeNum timeNumMean = mean(resultlist);
        long end = Mymethod.getZeroEndFistPara(timeType);
        end = Mymethod.zeroBegin(end, timeType);
        long subsection1 = (long) (end * 0.333);
        long subsection2 = (long) (end * 0.666);
        // 门槛1
        double threshold1 = Double.valueOf(timeNumMean.num) * (1 + 0);
        // 门槛2
        double threshold2 = Double.valueOf(timeNumMean.num) * (1 + 1);
        // 门槛3
        double threshold3 = Double.valueOf(timeNumMean.num) * (1 + 2);

        for (int i = 0; i < resultlist.size(); i++) {
            if (Long.parseLong(resultlist.get(i).time) < subsection1 && resultlist.get(i).num > threshold1) {
                resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num/2, resultlist.get(i).date));
                continue;
            }

            if (Long.parseLong(resultlist.get(i).time) >= subsection1 && Long.parseLong(resultlist.get(i).time) < subsection2 && resultlist.get(i).num > threshold2) {
                resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num, resultlist.get(i).date));
                continue;
            }

            if (Long.parseLong(resultlist.get(i).time) >= subsection2 && resultlist.get(i).num > threshold3) {
                resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num * 2, resultlist.get(i).date));
                continue;
            }
        }

    }

    // 根据均值加三倍标准差来过滤数据
    public static void filterDataBystandardDeviation(List<TimeNum> resultlist, double range) {
        if (resultlist.isEmpty()) return;
        double standardDeviationPara = newVariance(resultlist);

        TimeNum timeNumMean = mean(resultlist);

        double threshold = timeNumMean.num + range * standardDeviationPara;

        for (int i = 0; i < resultlist.size(); i++) {
            if (resultlist.get(i).getNum() > threshold) {
                if (i == 0)
                    resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num, resultlist.get(i).date));
                else if (i == resultlist.size() - 1) {
                    resultlist.set(i, new TimeNum(resultlist.get(i).time, timeNumMean.num, resultlist.get(i).date));
                } else {
                    resultlist.set(i, new TimeNum(resultlist.get(i).time, (int) ((resultlist.get(i - 1).num + resultlist.get(i + 1).num) / 2), resultlist.get(i).date));
                }
            }
        }

    }

    // 根据角度来过滤数据
    public static void filterDataByAngle(List<TimeNum> list) {
        double[] quadratic = new double[3];
        RegressionLine.computeQuadraticFittingPara(list, quadratic);
        double minValue = computeMiniAngle(list, quadratic);

        for (int i = 1; i < list.size() - 1; i++) {
            double tmp = computeAnge(list, i);
            if (tmp > 0 || Math.abs(tmp) < minValue)
                list.set(i, new TimeNum(list.get(i).time, (int) ((list.get(i - 1).num + list.get(i + 1).num) / 2), list.get(i).date));
        }
    }
    private static double computeMiniAngle(List<TimeNum> list, double[] quadratic) {
        if (list.isEmpty() && list.size() < 2) {
            System.out.println("position 范围 超出指定范围，无法计算...");
            return 0.0;
        }
        double minValue = Double.MIN_VALUE;
        double[] beginPoint = new double[2];
        double[] endPoint = new double[2];
        beginPoint[0] = Double.valueOf(list.get(0).time);
        endPoint[0] = Double.valueOf(list.get(list.size() - 1).time);
        beginPoint[1] = getqudraticValue(list.get(0).num, quadratic);
        endPoint[1] = getqudraticValue(list.get(list.size() - 1).num, quadratic);

        for (int i = 1; i < list.size() - 1; i++) {
            double[] mid = new double[2];
            mid[0] = Double.valueOf(list.get(i).time);
            mid[1] = getqudraticValue(list.get(i).num, quadratic);
            double tmp = 0.0;
            double vector1[] = new double[2];
            double vector2[] = new double[2];
            vector1[0] = beginPoint[0] - mid[0];
            vector1[1] = beginPoint[1] - mid[1];
            vector2[0] = endPoint[0] - mid[0];
            vector2[1] = endPoint[1] - mid[1];

            tmp = (vector1[0] * vector2[0] + vector1[1] * vector2[1]) /
                    (Math.sqrt(Math.pow(vector1[0], 2) + Math.pow(vector1[1], 2)) + Math.sqrt(Math.pow(vector2[0], 2) + Math.pow(vector2[1], 2)));

            if (tmp < minValue) minValue = tmp;
        }

        return minValue;
    }
    private static double getqudraticValue(double value, double[] quadratic) {
        return quadratic[0] + quadratic[1] * value + quadratic[2] * Math.pow(value, 2);
    }
    private static double computeAnge(List<TimeNum> list, int position) {
        if (list.isEmpty() && position == 0 && position >= list.size() - 1) {
            System.out.println("position 范围 超出指定范围，无法计算...");
            return 0.0;
        }
        double result = 0.0;
        double vector1[] = new double[2];
        double vector2[] = new double[2];
        vector1[0] = Double.valueOf(list.get(position - 1).time) - Double.valueOf(list.get(position).time);
        vector1[1] = list.get(position - 1).num - list.get(position).num;
        vector2[0] = Double.valueOf(list.get(position + 1).time) - Double.valueOf(list.get(position).time);
        vector2[1] = list.get(position + 1).num - list.get(position).num;

        result = (vector1[0] * vector2[0] + vector1[1] * vector2[1]) /
                (Math.sqrt(Math.pow(vector1[0], 2) + Math.pow(vector1[1], 2)) + Math.sqrt(Math.pow(vector2[0], 2) + Math.pow(vector2[1], 2)));

        return result;
    }


    /*-------------------------------- 一些通用处理方法 -------------------------------*/
    // 计算给定一组数据平均值,返回一个TimeNum 的平均值
    public static TimeNum mean(final List<TimeNum> resultlist) {
        if (resultlist.size() <= 0) return null;
        TimeNum result = new TimeNum("", 0, new Date());
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

    // 递增
    public static void numCumulation(List<TimeNum> list) {
        if (list.isEmpty()) return;
        for (int i = 1; i < list.size(); i++) {
            list.get(i).num += list.get(i - 1).num;
        }
    }

    // 插值
    public static void interpolation(List<TimeNum> list) {
        if (list.isEmpty()) return;
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i).getTime() - list.get(i - 1).getTime() > 1) {
                String time = String.valueOf(list.get(i).getTime() - 1);
                int num = (int) ((list.get(i).num + list.get(i - 1).num) / 2);
                list.add(i, new TimeNum(time, num, list.get(i).date));
                i = 1;
            }
        }
    }

    // 填补插值
    public static void interpolationBySide(List<TimeNum> list) {
        if (list.isEmpty()) return;
        for (int i = 1; i < list.size() - 1; i++) {
            if (list.get(i).getTime() - list.get(i - 1).getTime() > 1) {
                String time = String.valueOf(list.get(i).getTime() - 1);

                int beginNum = list.get(i - 1).num;
                int endNum = list.get(i).num;
                int mid = 0;
                if (beginNum + endNum < 4) {
                    mid = 1;
                }else {
                    mid = (int)Math.round(0.33333333*(beginNum + endNum));
                    int diff = (int) (Math.round(0.6666666*beginNum) + Math.round(0.6666666*endNum) + mid- beginNum -endNum);
                    beginNum = (int) Math.abs(Math.round(0.6666666*beginNum));
                    endNum = (int)Math.round(0.6666666*endNum);
                    if (diff > 0){
                        if (beginNum > diff){
                            beginNum -= diff;
                        }else if (endNum >  diff){
                            endNum -= diff;
                        }
                    }else {
                        endNum -= diff;
                    }

                }

                list.set(i-1,new TimeNum(list.get(i-1).time,beginNum,list.get(i-1).date));
                list.set(i,new TimeNum(list.get(i).time,endNum,list.get(i).date));
                list.add(i,new TimeNum(time,mid,list.get(i).date));

                i = i-1;
            }
        }
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

    // 计算标准差
    public static double getStandardDeviation(final List<TimeNum> resultlist) {
        if (resultlist.size() <= 0) return -1;
        TimeNum timeNumMean = mean(resultlist);
        double result = 0.0;
        double num = 0;
        for (int i = 1; i < resultlist.size(); i++) {
            num = resultlist.get(i).num;
            result += Math.pow(num - timeNumMean.num, 2);
        }
        result /= resultlist.size();
        return Math.sqrt(result);
    }


}
