package com.yin.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionRetry {

	int times() default 1;
	long waitTime() default 500;
	
	Class[] catchExceptions() default {};
	
}
