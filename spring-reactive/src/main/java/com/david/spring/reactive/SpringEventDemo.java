package com.david.spring.reactive;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sc on 2/27/2019.
 */
public class SpringEventDemo {

    public static void main(String[] args) {

        //默认同步费阻塞
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        //构件线程池
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        //切换成异步费阻塞
        multicaster.setTaskExecutor(executorService);

        //增加时间监听器
        multicaster.addApplicationListener(event->{
            // 事件监听
            System.out.printf("[线程 : %s] event : %s\n",
                    Thread.currentThread().getName(), // 当前执行线程名称
                    event);
        });

        //广播事件
        multicaster.multicastEvent(new PayloadApplicationEvent("hello source","hello payload"));
        multicaster.multicastEvent(new PayloadApplicationEvent("hello source","hello payload"));
        multicaster.multicastEvent(new PayloadApplicationEvent("hello source","hello payload"));

        //关闭线程池
        executorService.shutdown();

    }
}
