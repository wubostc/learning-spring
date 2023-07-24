package com.cc.sp2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.aop.support.Pointcuts;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* com.cc.sp2..*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void performBefore(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println("args = " + args);

        Signature signature = joinPoint.getSignature();
        System.out.println("signature = " + signature);

        Object target = joinPoint.getTarget();
        System.out.println("target = " + target);


        SourceLocation location = joinPoint.getSourceLocation();
        System.out.println("location = " + location);
    }
}
