package com.scs.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.scs.annotation.RespNotInterceptor;
import com.scs.common.resp.MyBizRespJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class MyControllerAdviceForSuccess implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getExecutable().getDeclaringClass() == MyControllerAdviceForFailure.class) {
            return false;
        }

        if (returnType.getDeclaringClass().isAnnotationPresent(RespNotInterceptor.class)) {
            return false;
        }

        if (returnType.getMethod() != null &&
                returnType.getMethod().isAnnotationPresent(RespNotInterceptor.class)) {
            return false;
        }


        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//
        if (returnType.getMethod() == null) {
            return body;
        }

        if (returnType.getMethod().isAnnotationPresent(RespNotInterceptor.class)) {
            return body;
        }

        if ("application/json".equals(selectedContentType.toString())) {
            return MyBizRespJson.success(body);
        }


        if (body instanceof String) {
            try {
                return new JsonMapper().writeValueAsString(MyBizRespJson.success(body));
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        }

        return body;
    }
}
