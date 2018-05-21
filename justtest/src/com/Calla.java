package com;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calla implements Callable<String> {
    @Override
    public String call() throws Exception {
        Future<Object> future = Executors.newFixedThreadPool(1).submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "hello";
            }
        });
        future.get();   // 堵塞直到获取到结果
        future.cancel(true);    // 任务未启动false、true都不会被执行，已启动，
        return null;
    }
}
