package com;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Nice {
    static class Sync extends AbstractQueuedSynchronizer{

    }

    public static void main(String[] args) throws InterruptedException {

        int[] f = {1,2,3};
        int[] c = {1,2,3};
        int[] w = {1,2,3};
        int m=3,v=3;

        for (int i = 1; i <= m; ++i)
            for (int j = 0; j <= v; ++j)//注意此循环与01背包的用一维数组表示的状态方程的区别，一个循环逆序，一个顺序
                if (j >= c[i])
                    f[j] = f[j]>(f[j - c[i]] + w[i]) ? f[j] : f[j - c[i]] + w[i];//完全背包的状态方程，可画图加深理解
        if (f[v]<0)//背包为装满
            System.out.println();
        else
            System.out.println();

    }
}
