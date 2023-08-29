package com.scs.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MyAspect {
//    @Pointcut("@annotation(com.scs.annotation.BizResp)")
//    public void pointcut() {
//
//    }
//
//    @Around(value = "pointcut()")
//    @ResponseBody
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Object obj = proceedingJoinPoint.proceed();
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", MyBizError.SUCCESS.getCode());
//        result.put("data", obj);
//
//        return new JSONObject(result).toJSONString();
//    }
}
