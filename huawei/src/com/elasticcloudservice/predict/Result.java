package com.elasticcloudservice.predict;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 结果存放类
 */
class Result {
    public Flavor flavor;
    public int flavorSum;

    public int getWeightFlavorSum() {
        if (flavorSum < 0) return 0;
        else return flavorSum;
    }

    // 把Result的结果集压入到第一波字符串数组里
    public static void getOneStepOfFlavor(List<Result> resultList, List<String> results) {
        int sum = 0;
        Collections.sort(resultList, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.flavor.kind < o2.flavor.kind)
                    return -1;
                else if (o1.flavor.kind > o2.flavor.kind)
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 0; i < resultList.size(); i++) {
            Result result = resultList.get(i);
            sum += result.getWeightFlavorSum();
            results.add(result.flavor.getFlavorName() + " " + result.getWeightFlavorSum());
        }
        results.add(0, String.valueOf(sum));
        results.add(" "); // 加一行空行
    }

    @Override
    public String toString() {
        return "Result{" +
                "flavor=" + flavor +
                ", flavorSum=" + flavorSum +
                '}';
    }

}
