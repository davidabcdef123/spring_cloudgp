package com.david.spring.reactive.webflux;

import java.util.stream.Stream;

/**
 * Created by sc on 2/27/2019.
 */
public class ReactorDemo {

    public static void main(String[] args) {
        Stream.of(1,2,3,4,5)//生产
        .map(String::valueOf)//处理
        .forEach(System.out::println)//消费
        ;
        System.exit(128);
        // for-each 必须实现 java.lang.Iterable
        // []、Collection
    }

    private static void println(Object message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }
}
