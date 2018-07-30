package com.elasticcloudservice.predict;

import java.util.LinkedList;
import java.util.List;

// 计算线性回归方程的类
class RegressionLine {

    List<Double> xx = new LinkedList<Double>();
    List<Double> yy = new LinkedList<Double>();

    public void addDataPoint(double x, double y) {
        xx.add(x);
        yy.add(y);
    }

    public void lineFitting(double[] fitResult) {
        if (fitResult.length != 2) return;
        Double[] x = xx.toArray(new Double[xx.size()]);
        Double[] y = yy.toArray(new Double[yy.size()]);
        double xmean = 0.0;
        double ymean = 0.0;
        int size = x.length;
        for (int i = 0; i < size; i++) {
            xmean += x[i];
            ymean += y[i];
        }
        xmean /= size;
        ymean /= size;

        double sumx2 = 0.0;
        double sumxy = 0.0;
        for (int i = 0; i < size; i++) {
            sumx2 += (x[i] - xmean) * (x[i] - xmean);
            sumxy += (y[i] - ymean) * (x[i] - xmean);
        }

        fitResult[0] = sumxy / sumx2;
        fitResult[1] = ymean - fitResult[0] * xmean;

    }

    /**
     * y = a0 + a1x + a2x^2
     * fitresult[0] 为 a0 ; fitresult[1] 为 a1 ; fitresult[2] 为 a2;
     */
    public void quadraticLineFitting(double[] fitresult) {
        if (fitresult.length != 3) return;
        final double MIDMAX = Double.MAX_VALUE / 2;
        fitresult[0] = 0.0;
        fitresult[1] = 0.0;
        fitresult[2] = 0.0;

        Double[] x = xx.toArray(new Double[xx.size()]);
        Double[] y = yy.toArray(new Double[yy.size()]);
        // 存储那三行数据
        double one[] = {0.0, 0.0, 0.0, 0.0};
        double two[] = {0.0, 0.0, 0.0, 0.0};
        double three[] = {0.0, 0.0, 0.0, 0.0};

        // 第一行数据已经解决
        for (int i = 0; i < x.length; i++) {
            one[0] += 1;
            one[1] += x[i];
            one[2] += x[i] * x[i];
            one[3] += y[i];
        }
        // 第二行数据已经解决
        for (int i = 0; i < x.length; i++) {
            two[0] += x[i];
            two[1] += x[i] * x[i];
            two[2] += x[i] * x[i] * x[i];
            two[3] += x[i] * y[i];

            if (two[0] > MIDMAX || two[1] > MIDMAX || two[2] > MIDMAX || two[3] > MIDMAX)
                substractionDieDai(two, one);
        }
        // 第三行数据已经解决
        for (int i = 0; i < x.length; i++) {
            three[0] += x[i] * x[i];
            three[1] += x[i] * x[i] * x[i];
            three[2] += x[i] * x[i] * x[i] * x[i];
            three[3] += x[i] * x[i] * y[i];

            if (three[0] > MIDMAX || three[1] > MIDMAX || three[2] > MIDMAX || three[3] > MIDMAX)
                substractionDieDai(three, one);
        }

        xiangXiao(one, two, 1);
        xiangXiao(one, three, 1);
        xiangXiao(two, three, 2);
        xiangXiao(three, two, 3);
        xiangXiao(three, one, 3);
        xiangXiao(two, one, 2);

        if (one[0] == 0) {
            fitresult[0] = 0;
        } else {
            fitresult[0] = one[3] / one[0];
        }

        if (two[1] == 0) {
            fitresult[1] = 0;
        } else {
            fitresult[1] = two[3] / two[1];
        }

        if (three[2] == 0) {
            fitresult[2] = 0;
        } else {
            fitresult[2] = three[3] / three[2];
        }
    }

    /**
     * y = a0 + a1x + a2x^2 + a3x^3
     */
    public void cubeLineFitting(double[] fitresult) {
        if (fitresult.length != 4) return;
        final double MIDMAX = Double.MAX_VALUE / 2;

        Double[] x = xx.toArray(new Double[xx.size()]);
        Double[] y = yy.toArray(new Double[yy.size()]);
        // 存储那三行数据
        double one[] = {0.0, 0.0, 0.0, 0.0, 0.0};
        double two[] = {0.0, 0.0, 0.0, 0.0, 0.0};
        double three[] = {0.0, 0.0, 0.0, 0.0, 0.0};
        double four[] = {0.0, 0.0, 0.0, 0.0, 0.0};

        // 第一行数据已经解决
        for (int i = 0; i < x.length; i++) {
            one[0] += 1;
            one[1] += x[i];
            one[2] += x[i] * x[i];
            one[3] += x[i] * x[i] * x[i];
            one[4] += y[i];
        }
        // 第二行数据已经解决
        for (int i = 0; i < x.length; i++) {
            two[0] += x[i];
            two[1] += x[i] * x[i];
            two[2] += x[i] * x[i] * x[i];
            two[3] += x[i] * x[i] * x[i] * x[i];
            two[4] += x[i] * y[i];

            if (two[0] > MIDMAX || two[1] > MIDMAX || two[2] > MIDMAX || two[3] > MIDMAX || two[4] > MIDMAX)
                substractionDieDai(two, one);
        }
        // 第三行数据已经解决
        for (int i = 0; i < x.length; i++) {
            three[0] += x[i] * x[i];
            three[1] += x[i] * x[i] * x[i];
            three[2] += x[i] * x[i] * x[i] * x[i];
            three[3] += x[i] * x[i] * x[i] * x[i] * x[i];
            three[4] += x[i] * x[i] * y[i];

            if (three[0] > MIDMAX || three[1] > MIDMAX || three[2] > MIDMAX || three[3] > MIDMAX || three[4] > MIDMAX)
                substractionDieDai(three, one);
        }

        // 第四行数据已经解决
        for (int i = 0; i < x.length; i++) {
            four[0] += x[i] * x[i] * x[i];
            four[1] += x[i] * x[i] * x[i] * x[i];
            four[2] += x[i] * x[i] * x[i] * x[i] * x[i];
            four[3] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            four[4] += x[i] * x[i] * x[i] * y[i];

            if (four[0] > MIDMAX || four[1] > MIDMAX || four[2] > MIDMAX || four[3] > MIDMAX || four[4] > MIDMAX)
                substractionDieDai(four, one);
        }

        xiangXiao(one, two, 1);
        xiangXiao(one, three, 1);
        xiangXiao(one, four, 1);
        xiangXiao(two, three, 2);
        xiangXiao(two, four, 2);
        xiangXiao(three, four, 3);

        xiangXiao(four, three, 4);
        xiangXiao(four, two, 4);
        xiangXiao(four, one, 4);
        xiangXiao(three, two, 3);
        xiangXiao(three, one, 3);
        xiangXiao(two, one, 2);


        if (one[0] == 0) {
            fitresult[0] = 0;
        } else {
            fitresult[0] = one[4] / one[0];
        }

        if (two[1] == 0) {
            fitresult[1] = 0;
        } else {
            fitresult[1] = two[4] / two[1];
        }

        if (three[2] == 0) {
            fitresult[2] = 0;
        } else {
            fitresult[2] = three[4] / three[2];
        }

        if (four[3] == 0) {
            fitresult[3] = 0;
        } else {
            fitresult[3] = four[4] / four[3];
        }
    }

    /**
     * y = a0 + a1x + a2x^2 + a3x^3 + a4x^4
     */
    public void biquadraticLineFitting(double[] fitresult) {
        if (fitresult.length != 5) return;
        final double MIDMAX = Double.MAX_VALUE / 2;

        Double[] x = xx.toArray(new Double[xx.size()]);
        Double[] y = yy.toArray(new Double[yy.size()]);
        // 存储那三行数据
        double one[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double two[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double three[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double four[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double five[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        // 第一行数据已经解决
        for (int i = 0; i < x.length; i++) {
            one[0] += 1;
            one[1] += x[i];
            one[2] += x[i] * x[i];
            one[3] += x[i] * x[i] * x[i];
            one[4] += x[i] * x[i] * x[i] * x[i];
            one[5] += y[i];
        }
        // 第二行数据已经解决
        for (int i = 0; i < x.length; i++) {
            two[0] += x[i];
            two[1] += x[i] * x[i];
            two[2] += x[i] * x[i] * x[i];
            two[3] += x[i] * x[i] * x[i] * x[i];
            two[4] += x[i] * x[i] * x[i] * x[i] * x[i];
            two[5] += x[i] * y[i];

            if (two[0] > MIDMAX || two[1] > MIDMAX || two[2] > MIDMAX || two[3] > MIDMAX || two[4] > MIDMAX || two[5] > MIDMAX)
                substractionDieDai(two, one);
        }
        // 第三行数据已经解决
        for (int i = 0; i < x.length; i++) {
            three[0] += x[i] * x[i];
            three[1] += x[i] * x[i] * x[i];
            three[2] += x[i] * x[i] * x[i] * x[i];
            three[3] += x[i] * x[i] * x[i] * x[i] * x[i];
            three[4] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            three[5] += x[i] * x[i] * y[i];

            if (three[0] > MIDMAX || three[1] > MIDMAX || three[2] > MIDMAX || three[3] > MIDMAX || three[4] > MIDMAX || three[5] > MIDMAX)
                substractionDieDai(three, one);
        }

        // 第四行数据已经解决
        for (int i = 0; i < x.length; i++) {
            four[0] += x[i] * x[i] * x[i];
            four[1] += x[i] * x[i] * x[i] * x[i];
            four[2] += x[i] * x[i] * x[i] * x[i] * x[i];
            four[3] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            four[4] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            four[5] += x[i] * x[i] * x[i] * y[i];

            if (four[0] > MIDMAX || four[1] > MIDMAX || four[2] > MIDMAX || four[3] > MIDMAX || four[4] > MIDMAX || four[5] > MIDMAX)
                substractionDieDai(four, one);
        }

        // 第五行数据已经解决
        for (int i = 0; i < x.length; i++) {
            five[0] += x[i] * x[i] * x[i] * x[i];
            five[1] += x[i] * x[i] * x[i] * x[i] * x[i];
            five[2] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            five[3] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            five[4] += x[i] * x[i] * x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            five[5] += x[i] * x[i] * x[i] * x[i] * y[i];

            if (five[0] > MIDMAX || five[1] > MIDMAX || five[2] > MIDMAX || five[3] > MIDMAX || five[4] > MIDMAX || five[5] > MIDMAX)
                substractionDieDai(five, one);
        }

        xiangXiao(one, two, 1);
        xiangXiao(one, three, 1);
        xiangXiao(one, four, 1);
        xiangXiao(one, five, 1);
        xiangXiao(two, three, 2);
        xiangXiao(two, four, 2);
        xiangXiao(two, five, 2);
        xiangXiao(three, four, 3);
        xiangXiao(three, five, 3);
        xiangXiao(four, five, 4);

        xiangXiao(five, four, 5);
        xiangXiao(five, three, 5);
        xiangXiao(five, two, 5);
        xiangXiao(five, one, 5);
        xiangXiao(four, three, 4);
        xiangXiao(four, two, 4);
        xiangXiao(four, one, 4);
        xiangXiao(three, two, 3);
        xiangXiao(three, one, 3);
        xiangXiao(two, one, 2);


        if (one[0] == 0) {
            fitresult[0] = 0;
        } else {
            fitresult[0] = one[5] / one[0];
        }

        if (two[1] == 0) {
            fitresult[1] = 0;
        } else {
            fitresult[1] = two[5] / two[1];
        }

        if (three[2] == 0) {
            fitresult[2] = 0;
        } else {
            fitresult[2] = three[5] / three[2];
        }

        if (four[3] == 0) {
            fitresult[3] = 0;
        } else {
            fitresult[3] = four[5] / four[3];
        }

        if (five[4] == 0) {
            fitresult[4] = 0;
        } else {
            fitresult[4] = five[5] / five[4];
        }
    }

    // 数组a 减去数组b,通过迭代达到目的
    private void substractionDieDai(double[] a, double[] b) {
        if (a == null || b == null) return;
        if (a.length != b.length) return;
        if (a[3] < b[3]) return;
        for (int i = 0; i < a.length; i++) {
            a[i] -= b[i];
        }
        substractionDieDai(a, b);

    }

    //  数组a 减去数组b
    private void substraction(double[] a, double[] b) {
        if (a == null || b == null) return;
        if (a.length != b.length) return;
        for (int i = 0; i < a.length; i++) {
            a[i] -= b[i];
        }

    }

    // b消去第个数，使之为零
    private void xiangXiao(double[] a, double[] b, int i) {
        i -= 1;
        if (b[i] == 0) return;
        double factor = a[i] / b[i];
        for (int j = 0; j < a.length; j++) {
            b[j] *= factor;
        }
        substraction(b, a);
    }


    // 计算线性拟合参数
    public static void computeQuadraticFittingPara(final List<TimeNum> xylist, double[] result) {
        if (xylist.isEmpty()) return;
        RegressionLine regressionLine = new RegressionLine();
        for (TimeNum timeNum : xylist) {
            float xtime = Float.valueOf(timeNum.time);
            float ynum = (float) timeNum.num;
            regressionLine.addDataPoint(xtime, ynum);
        }
        regressionLine.quadraticLineFitting(result);
    }

    // 计算二次拟合参数
    public static void computeLinearFittingPara(final List<TimeNum> xylist, double[] result) {
        if (xylist.isEmpty()) return;
        RegressionLine regressionLine = new RegressionLine();
        for (TimeNum timeNum : xylist) {
            float xtime = Float.valueOf(timeNum.time);
            float ynum = (float) timeNum.num;
            regressionLine.addDataPoint(xtime, ynum);
        }
        regressionLine.lineFitting(result);
    }

    // 计算三次拟合参数
    public static void computeCubicFittingPara(final List<TimeNum> xylist, double[] result) {
        if (xylist.isEmpty()) return;
        RegressionLine regressionLine = new RegressionLine();
        for (TimeNum timeNum : xylist) {
            float xtime = Float.valueOf(timeNum.time);
            float ynum = (float) timeNum.num;
            regressionLine.addDataPoint(xtime, ynum);
        }
        regressionLine.cubeLineFitting(result);
    }

    // 计算四次拟合参数
    public static void computeBiquadraticFittingPara(final List<TimeNum> xylist, double[] result) {
        if (xylist.isEmpty()) return;
        RegressionLine regressionLine = new RegressionLine();
        for (TimeNum timeNum : xylist) {
            float xtime = Float.valueOf(timeNum.time);
            float ynum = (float) timeNum.num;
            regressionLine.addDataPoint(xtime, ynum);
        }
        regressionLine.biquadraticLineFitting(result);
    }


}
