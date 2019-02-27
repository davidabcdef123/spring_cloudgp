package com.david.spring.reactive;

/**
 * Created by sc on 2/27/2019.
 */
public class UncaughtExceptionHandlerDemo {

    public static void main(String[] args) {
        Thread.currentThread().setUncaughtExceptionHandler((t,e)->{
            System.out.println(e.getMessage());
        });
        throw new RuntimeException("故意的！");
    }
}
