package com.elasticcloudservice.predict;

import java.util.LinkedList;
import java.util.List;

/**
 * 存储预测结果
 */
public class SemiResult {

    public List<List<TimeNum>> parentList;
    public Flavor flavor;
    long beginTime;
    long endTime;

    public SemiResult(List<List<TimeNum>> parentList, Flavor flavor, long beginTime, long endTime) {
        this.parentList = parentList;
        this.flavor = flavor;
        this.beginTime = beginTime;
        this.endTime = endTime;

    }

    public void getWhichQuadraticFitPara(int num, double[] fitPara) {
        RegressionLine.computeQuadraticFittingPara(parentList.get(num), fitPara);
    }

    public void getWhichLineFitPara(int num, double[] fitPara) {
        RegressionLine.computeLinearFittingPara(parentList.get(num), fitPara);
    }

    public void getWhichCubicFitPara(int num, double[] fitPara) {
        RegressionLine.computeCubicFittingPara(parentList.get(num), fitPara);
    }

    public void getWhichBiquadraticFitPara(int num, double[] fitPara) {
        RegressionLine.computeBiquadraticFittingPara(parentList.get(num), fitPara);
    }


    @Override
    public String toString() {
        return "SemiResult{" +
                "flavor=" + flavor + '}';
    }


    // 获取半成品结果
    public static List<SemiResult> getSemiResultList(InputeContent inputeContent, EcsDataNode[] ecsDataNodes, TimeType timeType) {
        long beginTime = Mymethod.zeroBegin(inputeContent.getBegin(timeType), timeType);
        long endTime = Mymethod.zeroBegin(inputeContent.getEnd(timeType), timeType);

        List<SemiResult> semiResultList = new LinkedList<>();
        for (Flavor flavor : inputeContent.flavorList) {

            List<List<TimeNum>> parentList = new LinkedList<>();
            List<TimeNum> childlist1 = new LinkedList<>();  // 累加数据
            List<TimeNum> childlist2 = new LinkedList<>();  // 累加数据
            List<TimeNum> childlist3 = new LinkedList<>();  // 无累加数据
            List<TimeNum> childlist4 = new LinkedList<>();  // 无累加数据
            List<TimeNum> childlist5 = new LinkedList<>();

            FilterData.clssifyOneByFlavorAndFilter(ecsDataNodes, childlist1, flavor.getFlavorName(), timeType, 2, true, 2); // 获取处理后的每条数据
            FilterData.clssifyOneByFlavorAndFilter(ecsDataNodes, childlist2, flavor.getFlavorName(), timeType, 3, true, 0); // 获取处理后的每条数据
            FilterData.clssifyOneByFlavorAndFilter(ecsDataNodes, childlist3, flavor.getFlavorName(), timeType, 1, false, 1); // 获取处理后的每条数据
            FilterData.clssifyOneByFlavorAndFilter(ecsDataNodes, childlist4, flavor.getFlavorName(), timeType, 0, false, 2); // 获取处理后的每条数据
            FilterData.clssifyOneByFlavorAndFilter(ecsDataNodes, childlist5, flavor.getFlavorName(), timeType, 0, false, 0); // 获取处理后的每条数据

            parentList.add(childlist1);
            parentList.add(childlist2);
            parentList.add(childlist3);
            parentList.add(childlist4);
            parentList.add(childlist5);

            SemiResult semiResult = new SemiResult(parentList, flavor, beginTime, endTime);
            semiResultList.add(semiResult);
        }

        return semiResultList;
    }

    // 通过半成品结果获取Result结果
    public static Result getSemiResult(SemiResult semiResult) {
        Result result = new Result();
        result.flavor = semiResult.flavor;

        int preferBig = 0;
        List<TimeNum> newTimnum = semiResult.parentList.get(4);
        double fangcha = FilterData.getStandardDeviation(newTimnum);
        double junzhi = FilterData.mean(newTimnum).num;

        for (int i = newTimnum.size()-1; i >=0 && i > newTimnum.size()-3; i--){
            if (newTimnum.get(i).num > (3*fangcha + junzhi)) preferBig = 1;
        }


        result.flavorSum = getNoAccumuQuadratic(semiResult,2);


        if (preferBig == 1){
            result.flavorSum -= 1;
        }

//        System.out.println(semiResult.flavor.getFlavorName() + ": " + result.flavorSum);

        return result;
    }


    // 通过四次方拟合的结果
    private static int getBiquadraticFlavorSum(SemiResult semiResult, int num) {
        if (semiResult == null) return 0;
        double flavorSum4 = 0.0;
        double[] biquadratic = new double[5];
        semiResult.getWhichBiquadraticFitPara(num, biquadratic);  // 计算第组数据
        flavorSum4 =
                biquadratic[1] * (semiResult.endTime - semiResult.beginTime) +
                        biquadratic[2] * (Math.pow(semiResult.endTime, 2) - Math.pow(semiResult.beginTime, 2)) +
                        biquadratic[3] * (Math.pow(semiResult.endTime, 3) - Math.pow(semiResult.beginTime, 3)) +
                        biquadratic[4] * (Math.pow(semiResult.endTime, 4) - Math.pow(semiResult.beginTime, 4));

        return (int) flavorSum4;
    }

    // 通过三次方拟合的结果
    private static int getCubicFlavorSum(SemiResult semiResult, int num) {
        if (semiResult == null) return 0;
        double flavorSum = 0.0;
        double[] cubic0 = new double[4];
        semiResult.getWhichCubicFitPara(num, cubic0); // 用第几组数据
//        System.out.println(semiResult.flavor.getFlavorName() + ": " + cubic0[0] + "\t"+ cubic0[1] + "\t"+ cubic0[2] + "\t"+ cubic0[3] + "\t");
        flavorSum =
                cubic0[1] * (semiResult.endTime - semiResult.beginTime) +
                        cubic0[2] * (Math.pow(semiResult.endTime, 2) - Math.pow(semiResult.beginTime, 2)) +
                        cubic0[3] * (Math.pow(semiResult.endTime, 3) - Math.pow(semiResult.beginTime, 3));
        return (int) flavorSum;
    }

    // 通过二次和一次拟合结果综合
    private static int getQuadraticLineFlavorSum(SemiResult semiResult, int num) {
        double flavorSum = 0.0;
        double[] quadratic1 = new double[3];
        double[] linepara1 = new double[2];
        semiResult.getWhichQuadraticFitPara(num, quadratic1);
        semiResult.getWhichLineFitPara(num, linepara1);
//        System.out.println(semiResult.flavor.getFlavorName() + ": " + quadratic1[0] + "\t"+ quadratic1[1] + "\t"+ quadratic1[2] + "\t");

        flavorSum = (0.5 * linepara1[0] + 0.5 * quadratic1[1]) * (semiResult.endTime - semiResult.beginTime)
                + quadratic1[2] * (semiResult.endTime - semiResult.beginTime) * (semiResult.beginTime + semiResult.endTime);

        return (int) flavorSum;
    }

    // 无累加一次二次总和所得
    private static int getNoAccumuQuadratic(SemiResult semiResult, int num) {
        // 没有累加的一次拟合和二次拟合
        double flavorSum2 = 0.0;
        double[] quadratic2 = new double[3];
        double[] linepara2 = new double[2];
        semiResult.getWhichQuadraticFitPara(num, quadratic2);
//        semiResult.getWhichLineFitPara(num, linepara2);

        double flavorSum22 = 0.0;
        // 以下是没有累加后的，计算计算结果 一次 和 二次拟合结果

        for (long i = semiResult.beginTime; i < semiResult.endTime; i++) {
            flavorSum22 += quadratic2[0] + quadratic2[1] * i + quadratic2[2] * i * i;    // 二次
        }

//        double flavorSum = (int) Math.round(probability(semiResult.parentList.get(1)) * (flavorSum2 + flavorSum22) / 2);   // 一次和二次结果中和

        return (int) flavorSum22;
    }

    // 没有累加的一次拟合所得结果
    private static int getNoAccumuLine(SemiResult semiResult, int num) {
        double flavorSum = 0.0;
        double[] linepara = new double[2];
        semiResult.getWhichLineFitPara(num, linepara);

        double flavorSum22 = 0.0;
        // 以下是没有累加后的，计算计算结果 一次 和 二次拟合结果
        for (long i = semiResult.beginTime; i < semiResult.endTime; i++) {
            flavorSum += linepara[0] * i + linepara[1]; // 一次
        }

        return (int) Math.round(flavorSum);
    }

}
