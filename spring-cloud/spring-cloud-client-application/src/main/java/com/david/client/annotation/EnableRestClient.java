package com.david.client.annotation;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by sc on 2019-03-05.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RestClientsRegistrar.class)
public @interface EnableRestClient {

    /**
     * 指定 @RestClient 接口
     * @return
     */
    Class<?>[] clients() default {};
}
