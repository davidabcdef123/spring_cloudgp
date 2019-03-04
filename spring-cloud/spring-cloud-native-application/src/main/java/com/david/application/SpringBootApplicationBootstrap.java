package com.david.application;

import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sc on 2019-03-04.
 */
@EnableAutoConfiguration
@RestController
public class SpringBootApplicationBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("憨豆");
        //在上下文中注册一个"helloWorld" String 类型的 Bean
        parentContext.registerBean("helloworld",String.class,"hello");
        //启动上下文
        parentContext.refresh();

        // 类比于 Spring WebMVC，Root WebApplication 和 DispatcherServlet WebApplication
        // DispatcherServlet WebApplication parent = Root WebApplication
        // DispatcherServlet Servlet
        // Filter -> Root WebApplication

        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class).parent(parentContext).run(args);
    }

    @Autowired
    @Qualifier("helloworld")//bean名称 来自于“憨豆”上下文
    private String message;

    @RequestMapping("")
    public String index(){
        return message;}

}
