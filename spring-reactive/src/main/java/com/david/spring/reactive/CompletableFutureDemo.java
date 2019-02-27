package com.david.spring.reactive;

import java.util.concurrent.CompletableFuture;

/**
 * Created by sc on 2/27/2019.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        println("当前线程");
        CompletableFuture.supplyAsync(()->{
            println("第一步返回 \"Hello\"");
            return "Hello";
        }).thenApplyAsync(result->{
            println("第二步在第一步结果 +\",World\"");
            return result + ",World";
        }).thenAccept(CompletableFutureDemo::println)
                .whenComplete((v,Error)->{// 返回值 void, 异常 -> 结束状态
                    println("执行结束");})
                .join();//等待执行结束

    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
