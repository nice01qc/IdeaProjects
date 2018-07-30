package com.elasticcloudservice.predict;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 处理服务器分配问题，用的是背包算法
 */
public class Knapsack {


    /**
     * 背包问题，以下6个函数均服务于背包，其中transferResultList用于转换数据
     */
    public static void knapsackProblemCPU(InputeContent inputeContent, List<Flavor> flavorList, List<String> mechineList) {

        int[] weight = new int[flavorList.size()];  //物品重量
        int[] val = new int[flavorList.size()];     //物品价值
        for (int i = 0; i < flavorList.size(); i++) {
            weight[i] = flavorList.get(i).memG;
            val[i] = flavorList.get(i).cpu;
        }
        int m = inputeContent.serverMemSize;  // 背包容量
        int n = val.length; // 物品的个数

        int[][] f = new int[n + 1][m + 1]; //f[i][j]表示前i个物品能装入容量为j的背包中的最大价值
        int[][] path = new int[n + 1][m + 1];
        //初始化第一列和第一行
        for (int i = 0; i < f.length; i++) {
            f[i][0] = 0;
        }
        for (int i = 0; i < f[0].length; i++) {
            f[0][i] = 0;
        }
        //通过公式迭代计算
        for (int i = 1; i < f.length; i++) {
            for (int j = 1; j < f[0].length; j++) {
                if (weight[i - 1] > j)
                    f[i][j] = f[i - 1][j];
                else {
                    if (f[i - 1][j] < f[i - 1][j - weight[i - 1]] + val[i - 1]) {
                        f[i][j] = f[i - 1][j - weight[i - 1]] + val[i - 1];
                        path[i][j] = 1;
                    } else {
                        f[i][j] = f[i - 1][j];
                    }

                }
            }
        }

        int tmp = 1;
        List<Integer> integerList = new LinkedList<Integer>();
        while (true) {
            integerList.clear();
            int i = f.length - tmp;
            int j = f[0].length - 1;
            while (i > 0 && j > 0) {
                if (path[i][j] == 1) {
                    integerList.add(i);
                    j -= weight[i - 1];
                }
                i--;
            }
            tmp++;
            if (panduanResultCPU(inputeContent, integerList, flavorList)) break;
        }

        addmechineMessage(inputeContent, integerList, flavorList, mechineList);
        if (!flavorList.isEmpty()) knapsackProblemCPU(inputeContent,flavorList, mechineList);   // 如果 还有继续迭代
    }

    public static void knapsackProblemMEM(InputeContent inputeContent, List<Flavor> flavorList, List<String> mechineList) {

        int[] weight = new int[flavorList.size()];  //物品重量
        int[] val = new int[flavorList.size()];     //物品价值
        for (int i = 0; i < flavorList.size(); i++) {
            weight[i] = flavorList.get(i).cpu;
            val[i] = flavorList.get(i).memG;
        }
        int m = inputeContent.serverCpuNum;  // 背包容量
        int n = val.length; // 物品的个数

        int[][] f = new int[n + 1][m + 1]; //f[i][j]表示前i个物品能装入容量为j的背包中的最大价值
        int[][] path = new int[n + 1][m + 1];
        //初始化第一列和第一行
        for (int i = 0; i < f.length; i++) {
            f[i][0] = 0;
        }
        for (int i = 0; i < f[0].length; i++) {
            f[0][i] = 0;
        }
        //通过公式迭代计算
        for (int i = 1; i < f.length; i++) {
            for (int j = 1; j < f[0].length; j++) {
                if (weight[i - 1] > j)
                    f[i][j] = f[i - 1][j];
                else {
                    if (f[i - 1][j] < f[i - 1][j - weight[i - 1]] + val[i - 1]) {
                        f[i][j] = f[i - 1][j - weight[i - 1]] + val[i - 1];
                        path[i][j] = 1;
                    } else {
                        f[i][j] = f[i - 1][j];
                    }
                    //f[i][j] = Math.max(f[i-1][j], f[i-1][j-weight[i-1]]+val[i-1]);
                }
            }
        }

        int tmp = 1;
        List<Integer> integerList = new LinkedList<Integer>();
        while (true) {
            integerList.clear();
            int i = f.length - tmp;
            int j = f[0].length - 1;
            while (i > 0 && j > 0) {
                if (path[i][j] == 1) {
                    integerList.add(i);
                    j -= weight[i - 1];
                }
                i--;
            }
            tmp++;
            if (panduanResultMEM(inputeContent,integerList, flavorList)) break;
        }

        addmechineMessage(inputeContent, integerList, flavorList, mechineList);

        if (!flavorList.isEmpty()) knapsackProblemMEM(inputeContent, flavorList, mechineList);   // 如果 还有继续迭代
    }

    public static boolean panduanResultCPU(InputeContent inputeContent, List<Integer> integerList, List<Flavor> flavorList) {
        int memSum = 0;
        int cpuSum = 0; // test
        for (Integer x : integerList) {
            memSum += flavorList.get(x - 1).memG;
            cpuSum += flavorList.get(x - 1).cpu;
        }


        if (cpuSum <= inputeContent.serverCpuNum) {
//            System.out.println(cpuSum + " cpu " + ecsContentData.serverCpuNum + "+++++" + memSum + " MEM " + ecsContentData.serverMemSize);
            return true;
        } else return false;
    }

    public static boolean panduanResultMEM(InputeContent inputeContent,List<Integer> integerList, List<Flavor> flavorList) {
        int memSum = 0;
        int cpuSum = 0; // test
        for (Integer x : integerList) {
            memSum += flavorList.get(x - 1).memG;
            cpuSum += flavorList.get(x - 1).cpu;
        }


        if (memSum <= inputeContent.serverMemSize) {
//            System.out.println(memSum + " MEM " + ecsContentData.serverMemSize+ "+++++" + cpuSum + " cpu " + ecsContentData.serverCpuNum);
            return true;
        } else return false;
    }

    public static List<Flavor> transferResultList(List<Result> resultList) {
        if (resultList.isEmpty()) return null;

        List<Flavor> flavorList = new LinkedList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Result result = resultList.get(i);
            for (int j = 0; j < result.getWeightFlavorSum(); j++) {
                flavorList.add(result.flavor);
            }
        }
        return flavorList;
    }

    public static void addmechineMessage(InputeContent inputeContent, List<Integer> integerList, List<Flavor> flavorList, List<String> mechineList) {
        Collections.sort(integerList);  // 将数据由小到大排序
        String realList = "";
        for (Flavor flavor : inputeContent.flavorList) {
            List<Flavor> tmplist = new LinkedList<Flavor>();
            for (int i = 0; i < integerList.size(); i++) {
                int index = integerList.get(i) - 1;
                if (flavorList.get(index).kind == flavor.kind) tmplist.add(flavorList.get(index));
            }
            if (!tmplist.isEmpty()) {
                realList += tmplist.get(0).getFlavorName() + " " + tmplist.size() + " ";
            }
        }
        mechineList.add(realList);
        // 删除这批数据
        List<Flavor> newFlavorList = new LinkedList<Flavor>();
        for (int i = 0; i < flavorList.size(); i++) {
            if (integerList.contains(i + 1)) continue;
            newFlavorList.add(flavorList.get(i));
        }
        flavorList.clear();
        flavorList.addAll(newFlavorList);
    }








}
