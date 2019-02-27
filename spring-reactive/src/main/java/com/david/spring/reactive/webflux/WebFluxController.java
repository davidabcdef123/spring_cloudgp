package com.david.spring.reactive.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

import java.util.function.Supplier;

/**
 * Created by sc on 2/27/2019.
 */
@RestController
public class WebFluxController {


    /**
     * 执行结果
     * [线程 : reactor-http-nio-2] 执行计算
     * [线程 : reactor-http-nio-2] 返回结果
     * 并没有切换线程所以说webfuux是同步/异步 非阻塞的
     * @return
     */
    @RequestMapping("")
    public Mono<String> index(){
        println("执行计算");
        Mono<String> result=Mono.fromSupplier(()->{
            println("返回结果");
            return "hello world";
        });

        return result;
    }

    private static void println(String message) {
        System.out.printf("[线程 : %s] %s\n",
                Thread.currentThread().getName(), // 当前线程名称
                message);
    }

}
