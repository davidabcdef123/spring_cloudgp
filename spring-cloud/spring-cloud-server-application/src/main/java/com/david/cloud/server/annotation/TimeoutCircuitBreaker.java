package com.david.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * Created by sc on 2019-03-11.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeoutCircuitBreaker {

    /**
     * 超时时间
     * @return 设置超时时间
     */
    long timeout();
}
