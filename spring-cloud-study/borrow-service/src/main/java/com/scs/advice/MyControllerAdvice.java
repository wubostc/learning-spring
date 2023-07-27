package com.scs.advice;


import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.scs.common.exception.MyBusinessException;
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
public class MyControllerAdvice {

    private String CODE = "code";
    private String MSG = "msg";
    private String TRACE_ID = "traceId";

    @ExceptionHandler
    @ResponseBody
    public Map errorHandler(Exception e) {
        Map map = new HashMap<>();
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
        } else if (e instanceof HystrixRuntimeException) {
            HystrixRuntimeException e0 = (HystrixRuntimeException) e;
            Throwable e1 = e0.getFallbackException();

            if (e1 instanceof MyBusinessException) {
                map.put(CODE, ((MyBusinessException) e1).getCode());
                map.put(MSG, ((MyBusinessException) e1).getMsg());
            }
        }

        if (!map.containsKey(CODE)) {
            map.put(CODE, -1);
            map.put(MSG, e.getMessage());
        }

        String rid = UUID.randomUUID().toString();
        map.put(TRACE_ID, rid);

        log.error(rid + "#" + map.get(CODE) + "#" + map.get(MSG), e);

        return map;
    }
}
