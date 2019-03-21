package com.david.cloud.server.aop;

import com.david.cloud.server.annotation.SemaphoreCircuitBreaker;
import com.david.cloud.server.annotation.TimeoutCircuitBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import sun.awt.ModalExclude;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/**
 * Created by sc on 2019-03-11.
 */
@Aspect
@Component
public class ServerControllerAspect {

    private ExecutorService executorService = newFixedThreadPool(20);

    private volatile Semaphore semaphore=null;
    //com.david.cloud.server.controller.ServerController

    @Around("execution(* com.david.cloud.server.controller.ServerController.advancedSay(..)) && args(message) ")
    public Object advancedSayInTimeout(ProceedingJoinPoint point,String message)throws Throwable{
        return doInvoke(point,message,100);
    }

    @Around("execution(* com.david.cloud.server.controller.ServerController.advancedSay2(..)) && args(message) ")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point,
                                        String message) throws Throwable {
        long timeout=-1;
        if(point instanceof MethodInvocationProceedingJoinPoint){
            MethodInvocationProceedingJoinPoint methodPoint= (MethodInvocationProceedingJoinPoint) point;
            MethodSignature signature = (MethodSignature) methodPoint.getSignature();
            Method method=signature.getMethod();
            TimeoutCircuitBreaker circuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
            timeout=circuitBreaker.timeout();
        }
        return doInvoke(point, message, timeout);
    }

    @Around("execution(* com.david.cloud.server.controller.ServerController.advancedSay3(..)) && args(message)" +
            " && @annotation(circuitBreaker) ")
    public Object advancedSay3InSemaphore(ProceedingJoinPoint point, String message, SemaphoreCircuitBreaker circuitBreaker) throws Throwable{
        int value=circuitBreaker.value();
        if (semaphore == null) {
            semaphore = new Semaphore(value);
        }
        Object returnValue=null;
        if(semaphore.tryAcquire()){
            returnValue=point.proceed(new Object[]{message});
            Thread.sleep(1000);
        }else{
            returnValue = errorContent("");
        }
        semaphore.release();
        return returnValue;
    }

    //todo presectroy是什么的销毁？jvm实例？
    @PreDestroy
    public void destroy(){
        executorService.shutdown();
    }

    public String errorContent(String message) {
        System.out.println("---------------------------------------->"+message);
        return "ServerControllerAspect Fault";
    }

    private Object doInvoke(ProceedingJoinPoint point, String message, long timeout) {
        Future<Object> future=executorService.submit(()->{
            Object returnValue=null;
            try {
                returnValue=point.proceed(new Object[]{message});
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return returnValue;
        });
        Object returnValue=null;
        try {
            returnValue=future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            future.cancel(true);//取消执行
            e.printStackTrace();
            returnValue = errorContent("");
        }
        return returnValue;
    }

}
