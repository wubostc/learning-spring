package com.scs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.scs.common.resp.MyBizRespJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功默认handler
 */
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {
    static final ObjectWriter writer = new ObjectMapper().writer();

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String s = writer.writeValueAsString(MyBizRespJson.success(authentication));
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(s);
    }
}
