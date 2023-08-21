package com.scs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.scs.common.resp.MyBizRespJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功默认实现
 */
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    static final ObjectWriter writer = new ObjectMapper().writer();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Object result = MyBizRespJson.success(authentication);

        String s = writer.writeValueAsString(result);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(s);
    }
}
