package com.scs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.scs.common.resp.MyBizRespJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证失败默认实现
 */
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    static final ObjectWriter writer = new ObjectMapper().writer();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Object result = MyBizRespJson.fail(exception.getMessage());
        String s = writer.writeValueAsString(result);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(s);
    }
}
