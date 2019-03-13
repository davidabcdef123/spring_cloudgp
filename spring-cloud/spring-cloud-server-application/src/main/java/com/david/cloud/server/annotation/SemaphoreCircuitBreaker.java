package com.david.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * Created by sc on 2019-03-11.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)// 运行时保存注解信息
@Documented
public @interface SemaphoreCircuitBreaker {

    /**
     * 信号量
     *
     * @return 设置超时时间
     */
    int value();
}
