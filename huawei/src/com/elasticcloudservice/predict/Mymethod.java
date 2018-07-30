package com.elasticcloudservice.predict;

import java.util.*;

/**
 * 整个程序在这个主程序中加载进来
 */
public class Mymethod {

    static InputeContent inputeContent = null;
    static EcsDataNode[] ecsDataNodes = null;

    // 初始化参数，那两个输入数据
    public static void initParam(String[] ecsContent, String[] inputContent) {
        inputeContent = getInputNodes(inputContent);  // 获取服务器相关信息
        getEcsContent(ecsContent);
    }

    // 主方法接口
    public static void mainMethod(List<String> results, TimeType timeType) {
        // 第一部分：每种虚拟机预测
        List<Result> resultList = new LinkedList<>();
        List<SemiResult> semiResultList = SemiResult.getSemiResultList(inputeContent, ecsDataNodes, timeType);

        for (SemiResult semiResult : semiResultList) {
            resultList.add(SemiResult.getSemiResult(semiResult));
        }

        Result.getOneStepOfFlavor(resultList, results);     // 将结果正式加入到result

        // 第二部分：虚拟机分配
        List<String> mechineList = new LinkedList<String>();  // 后续 机器配置信息放置处

        if (inputeContent.ourAim.equals("CPU")) {  // 判断以 优化cpu 还是 mem
            Knapsack.knapsackProblemCPU(inputeContent, Knapsack.transferResultList(resultList), mechineList);
        } else {
            Knapsack.knapsackProblemMEM(inputeContent, Knapsack.transferResultList(resultList), mechineList);
        }

        results.add(String.valueOf(mechineList.size()));    // 将机器配置信息加入

        for (int i = 0; i < mechineList.size(); i++) {
            results.add("" + (i + 1) + " " + mechineList.get(i));
        }

    }


    // 分解和提取 ecsContent 数据
    public static InputeContent getInputNodes(String[] inputContent) {
        InputeContent ecsContentData = new InputeContent();
        List<Flavor> list = new LinkedList<Flavor>();
        boolean pan = true;
        for (int i = 0; i < inputContent.length; i++) {
            if (i == 0) {
                String[] array = inputContent[0].trim().split(" ");
                if (array.length != 3) {
                    break;
                }
                ecsContentData.serverCpuNum = Integer.parseInt(array[0]);
                ecsContentData.serverMemSize = Integer.parseInt(array[1]);
                ecsContentData.serverHDSize = Integer.parseInt(array[2]);
                continue;
            }

            if (inputContent[i].contains(" ") && inputContent[i].split(" ").length == 3) {
                String[] array = inputContent[i].split(" ");
                Flavor flavor = new Flavor(Integer.parseInt(array[0].replaceAll("flavor", "")), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
                list.add(flavor);
                continue;
            }

            if (inputContent[i].trim().contains("CPU") || inputContent[i].trim().contains("MEM")) {
                ecsContentData.ourAim = inputContent[i].trim();
                continue;
            }

            if (inputContent[i].trim().contains("-")) {
                if (pan) {
                    ecsContentData.begin = inputContent[i];
                    pan = false;
                } else {
                    ecsContentData.end = inputContent[i];
                }
            }

        }
        ecsContentData.flavorList = list;
        return ecsContentData;
    }

    // 分解和提取 inputNodes 数据
    public static void getEcsContent(String[] ecsContent) {
        ecsDataNodes = new EcsDataNode[ecsContent.length];    // 此处初始化所有数据
        for (int i = 0; i < ecsContent.length; i++) {
            ecsContent[i] = ecsContent[i].replaceAll("[ \t]+", " ");
            String[] array = ecsContent[i].split(" ");
            EcsDataNode node = new EcsDataNode(array[0], array[1], array[2] + " " + array[3]);
            ecsDataNodes[i] = node;
        }
    }


    // 使每个值都减去 总数据的第一个值，与归一化相似
    public static void zeroBegin(List<TimeNum> resultlist, TimeType timeType) {
        if (resultlist.isEmpty()) return;
        long begin = getZeroBeginFistPara(timeType);
        for (TimeNum x : resultlist) {
            x.time = String.valueOf(Integer.valueOf(x.time) - begin);
        }
    }
    public static long zeroBegin(long time, TimeType timeType) {
        long begin = getZeroBeginFistPara(timeType);
        return time - begin;
    }

    // 通过给定时间类型，获取相应的总数据第一个数据 时间值
    public static long getZeroBeginFistPara(TimeType timeType) {
        long result = 0;
        result = ecsDataNodes[0].getDateByTimeType(timeType);
        return result - 1;
    }

    // 通过给定时间类型，获取相应的总数据最后一个数据 时间值
    public static long getZeroEndFistPara(TimeType timeType) {
        long result = ecsDataNodes[ecsDataNodes.length - 1].getDateByTimeType(timeType);
        return result - 1;
    }


    // ---------------------------------一下均用于测试-----------------------------/

}
