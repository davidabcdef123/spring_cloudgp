package com.david.cloud.server.controller;

import com.david.cloud.server.annotation.SemaphoreCircuitBreaker;
import com.david.cloud.server.annotation.TimeoutCircuitBreaker;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by sc on 2019-03-11.
 */
@RestController
public class ServerController {

    private final static Random random = new Random();

    @Value("${spring.application.name}")
    private String currentServgiceName;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    //简易版本
    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return dosay2(message);
        });
        //100 毫秒超时
        String returnValue = null;
        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // 超级容错 = 执行错误 或 超时
            returnValue = errorContent(message);
        }
        return returnValue;
    }

    //简易版本-通过抛出异常用
    @GetMapping("/say3")
    public String say3(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return dosay2(message);
        });
        //100 毫秒超时
        String returnValue = future.get(100, TimeUnit.MILLISECONDS);

        return returnValue;
    }
    /**
     * 中级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    //没有CircuitBreakerControllerAdvice之前这样会进拦截器但是会报500 应该是servlet协议的问题
    @GetMapping("/middle/say")
    public String middleSay(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return dosay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;

        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            future.cancel(true); // 取消执行
            throw e;
        }
        return returnValue;
    }

    //没有CircuitBreakerControllerAdvice之前这样会进拦截器但是会报500 应该是servlet协议的问题
    @GetMapping("/middle/say2")
    public String middleSay2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return dosay2(message);
        });
        // 100 毫秒超时
        String returnValue = future.get(100, TimeUnit.MILLISECONDS);
        return returnValue;
    }


    /**
     * 高级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {
        return dosay2(message);
    }

    /**
     * 高级版本 + 注解（超时）
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say2")
    @TimeoutCircuitBreaker(timeout = 100)
    public String advancedSay2(@RequestParam String message) throws Exception {
        return dosay2(message);
    }

    /**
     * 高级版本 + 注解（信号量）
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say3")
    @SemaphoreCircuitBreaker(1)
    public String advancedSay3(@RequestParam String message) throws Exception {
        return dosay2(message);
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
            }
    )
    @GetMapping("/say")
    public String say(@RequestParam String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);

        System.out.println("say() costs " + value + " ms.");

        // > 100
        Thread.sleep(value);

        System.out.println("ServerController 接收到消息 - say : " + message);
        return "Hello, " + message;

    }
    private String dosay2(String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);
        System.err.println("say2() costs " + value + " ms.");
        // > 100
        Thread.sleep(value);
        String returnValue = "Say 2 : " + message;
        System.err.println("dosay2="+returnValue);
        return returnValue;
    }

    public String errorContent(String message) {
        return "serverControler  Fault";
    }

}
