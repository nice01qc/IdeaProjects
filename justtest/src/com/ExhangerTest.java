package com;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExhangerTest {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String A = "bank A";
                    System.out.println("1: " + exgr.exchange(A));;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String B = "bank B";
                    System.out.println("2: " + exgr.exchange(B));;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
