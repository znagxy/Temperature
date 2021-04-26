package com.yin.config;

import java.lang.reflect.Method;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionRetryAspect {
	
	@Pointcut("@annotation(com.yin.config.ExceptionRetry)")
    public void retryPointCut() {
    }

	@Around("retryPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ExceptionRetry retry = method.getAnnotation(ExceptionRetry.class);
        String name = method.getName();
        Object[] args = joinPoint.getArgs();
        int times = retry.times();
        long waitTime = retry.waitTime();
        Class[] catchExceptions = retry.catchExceptions();
        // check param
        if (times <= 0) {
            times = 1;
        }
        for (; times > 0; times--) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                // 如果需要抛出异常，而且需要捕获的异常为空那就需要再抛出
                if (catchExceptions.length > 0) {
                    boolean needCatch = false;
                    for (Class catchException : catchExceptions) {
                        if (e.getClass() == catchException) {
                            needCatch = true;
                            break;
                        }
                    }
                    if (!needCatch) {
                        throw e;
                    }
                }
                // 如果接下来没有重试机会的话，直接报错
                if (times <= 1) {
                    throw e;
                }
                // 休眠 等待下次执行
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            }
        }
        return false;
    }
}
