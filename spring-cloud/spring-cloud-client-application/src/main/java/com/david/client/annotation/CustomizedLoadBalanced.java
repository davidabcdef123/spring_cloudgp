package com.david.client.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * Created by sc on 2019-03-05.
 * 负载均衡
 */
//Inherited 继承
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface CustomizedLoadBalanced {
}
