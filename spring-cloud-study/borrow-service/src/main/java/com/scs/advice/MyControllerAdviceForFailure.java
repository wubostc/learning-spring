package com.scs.advice;


//import com.netflix.hystrix.exception.HystrixRuntimeException;

import com.scs.common.error.MyBizError;
import com.scs.common.exception.MyBusinessException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.*;

@ControllerAdvice
@Slf4j
public class MyControllerAdviceForFailure {

    static private final String CODE = "code";
    static private final String MSG = "msg";
    static private final String TRACE_ID = "traceId";

    @ExceptionHandler
    @ResponseBody
    public Map errorHandler(Exception e) {
        Map<String, Object> map = new HashMap<>();
        if (e instanceof MyBusinessException) {
            map.put(CODE, ((MyBusinessException) e).getCode());
            map.put(MSG, ((MyBusinessException) e).getMsg());
        } else if (e instanceof ValidationException) {
            // GET 参数校验
            if (e instanceof ConstraintViolationException) {
                ConstraintViolationException e0 = (ConstraintViolationException) e;
                List<String> list = new ArrayList<>(e0.getConstraintViolations().size());

                map.put(CODE, 1001);
                map.put(MSG, list);

                e0.getConstraintViolations().forEach(error -> {
                    String msg = error.getMessage();
                    list.add(msg);
                });
            } else {
                map.put(CODE, 1001);
                map.put(MSG, e.getMessage());
            }
        } else if (e instanceof BindException) {
            // POST 参数校验
            BindException e0 = ((BindException) e);

            BindingResult result = e0.getBindingResult();


            if (result.hasErrors()) {
                List<String> list = new ArrayList<>(result.getErrorCount());
                result.getFieldErrors().forEach((error) -> {
                    String msg = error.getDefaultMessage();
                    list.add(msg);
                });
                map.put(CODE, 1001);
                map.put(MSG, list);
            }
        } else if (e instanceof FeignException) {
            FeignException e0 = (FeignException) e;

            map.put(CODE, e0.status());

            // 消息例如：
            // [503] during [GET] to [http://user-service/user/1] [UserClient#getUserById(Integer)]: [Load balancer does not contain an instance for the service user-service]
            map.put(MSG, e0.getMessage());
        }

//        } else if (e instanceof HystrixRuntimeException) {
//            HystrixRuntimeException e0 = (HystrixRuntimeException) e;
//            Throwable e1 = e0.getFallbackException();
//
//            if (e1 instanceof MyBusinessException) {
//                map.put(CODE, ((MyBusinessException) e1).getCode());
//                map.put(MSG, ((MyBusinessException) e1).getMsg());
//            }
//        }


        if (!map.containsKey(CODE)) {
            map.put(CODE, MyBizError.FAILURE.getCode());
            if (e.getMessage() == null && e.getCause() != null) {
                Throwable cause = e.getCause();
                map.put(MSG, cause.getMessage());
            } else {
                map.put(MSG, e.getMessage());
            }
        }

        String rid = UUID.randomUUID().toString();
        map.put(TRACE_ID, rid);

        log.error(rid + "#" + map.get(CODE) + "#" + map.get(MSG), e);

        return map;
    }
}
