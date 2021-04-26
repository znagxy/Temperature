package com.yin.config;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.slf4j.Slf4j;

@Component
@Scope
@Aspect
public class LimitAspect {
	
	private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER  = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;

    @Pointcut("@annotation(com.yin.config.MyLimit)")
    public void serviceLimit() {
    	
    }
    
    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        Signature sig = point.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        MyLimit annotation = currentMethod.getAnnotation(MyLimit.class);
        double limitNum = annotation.limitNum(); 
        
        if(RATE_LIMITER.size()==0) {
        	RATE_LIMITER.put("limit", rateLimiter.create(limitNum));
        }
        rateLimiter = RATE_LIMITER.get("limit");
        if(rateLimiter.tryAcquire()) {
            return point.proceed();
        } else {
            System.out.println("block the request...");
            return null;
        }
    }


}
