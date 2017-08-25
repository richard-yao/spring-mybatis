package com.richard.java8use.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月25日 下午2:53:22
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface ItcastResource {

	public String name() default "";
}
