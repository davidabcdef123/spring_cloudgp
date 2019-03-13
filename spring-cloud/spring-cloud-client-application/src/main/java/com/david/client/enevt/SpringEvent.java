package com.david.client.enevt;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by sc on 2019-03-11.
 */
public class SpringEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Object.class);
        context.addApplicationListener(e->{
            System.err.println("监听："+e.getClass().getSimpleName());
        });
        context.refresh();
        context.start();
        context.stop();
        context.close();
    }
}
