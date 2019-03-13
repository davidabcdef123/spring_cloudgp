package com.david.client.annotation;

import java.lang.annotation.*;

/**
 * Created by sc on 2019-03-05.
 * Rest Client 注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * REST 服务应用名称
     * @return
     */
    String name();
}
