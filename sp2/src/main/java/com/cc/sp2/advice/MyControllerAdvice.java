package com.cc.sp2.advice;

import com.cc.sp2.exception.MyBusinessException;
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


    @ExceptionHandler
    @ResponseBody
    public Map errorHandler(Exception e) {
        Map<Object, Object> map = new HashMap<>();
        if (e instanceof MyBusinessException) {
            map.put("code", ((MyBusinessException) e).getCode());
            map.put("msg", ((MyBusinessException) e).getMsg());
        } else if (e instanceof ValidationException) {
            // GET 参数校验
            if (e instanceof ConstraintViolationException) {
                ConstraintViolationException e0 = (ConstraintViolationException) e;
                List<String> list = new ArrayList<>(e0.getConstraintViolations().size());

                map.put("code", 1001);
                map.put("msg", list);

                e0.getConstraintViolations().forEach(error -> {
                    String msg = error.getMessage();
                    list.add(msg);
                });
            } else {
                map.put("code", 1001);
                map.put("msg", e.getMessage());
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
                map.put("code", 1001);
                map.put("err", list);
            }
        } else {

            map.put("code", -1);
            map.put("msg", "an error occurred: " + e.getMessage());
        }
        String rid = UUID.randomUUID().toString();
        map.put("rid", rid);

        log.error("rid:" + rid, e.getMessage(), e);

        return map;
    }
}
