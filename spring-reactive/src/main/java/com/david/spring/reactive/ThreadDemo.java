package com.david.spring.reactive;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sc on 2/27/2019.
 */
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {

        println("hello world 1");
        AtomicBoolean done = new AtomicBoolean(false);
        final boolean isDone;

        // volatile 易变，线程安全（可见性）不一定
        // final 不变，线程安全（一直不变）
        // final + volatile  = impossible

        Thread thread=new Thread(()->{
            // 线程任务
            println("Hello,World 2018");
            // CAS
            done.set(true);  // 不通用
        });

        thread.setName("sub-thread");//线程名字
        thread.start();//启动线程
        thread.join();//等待线程销毁

        println("Hello,World 2");
    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
