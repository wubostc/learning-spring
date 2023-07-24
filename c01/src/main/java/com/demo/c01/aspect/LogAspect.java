package com.demo.c01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Component
@Aspect
public class LogAspect {

    @Before(value = "execution(* com.demo.c01.Service.*.*(..))")
    public void sysLog(JoinPoint jp) {
        StringJoiner log = new StringJoiner("|", "{", "}");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.add(dtf.format(LocalDateTime.now()));

        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            log.add(arg == null ? "-" : arg.toString());
        }

        System.out.println("日志： " + log);
    }

    @AfterReturning(value = "execution(* com.demo.c01.Service.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        System.out.println("方法结果是：" + result);
    }
}
