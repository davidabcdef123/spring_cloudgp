package com.david.client.enevt;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * Created by sc on 2019-03-11.
 */
public class SpringAnnotationDrivenEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // SpringAnnotationDrivenEvent 注册为 Spring Bean
        context.register(SpringAnnotationDrivenEvent.class);
        context.refresh(); // 启动上下文
        context.publishEvent(new MyApplicationEvent("hello world"));
        // 确保上下文启动完毕后，再发送事件

    }

    private static class MyApplicationEvent extends ApplicationEvent{

        public MyApplicationEvent(Object source) {
            super(source);
        }
    }

    @EventListener
    public void onMessage(Object eventSource){
        System.err.println("监听到 MyApplicationEvent 事件源 : " + eventSource);
    }
}
