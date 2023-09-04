package com.scs.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scs.common.exception.MyBusinessException;
import com.scs.security.exception.MyAuthenticationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    public final static String VERIFY_CODE_KEY = "verifyCode";
    public final static String KAPTCHA_KEY = "kaptcha";

    public static String getVerifyCodeKey() {
        return verifyCodeKey;
    }

    public static void setVerifyCodeKey(String verifyCodeKey) {
        LoginFilter.verifyCodeKey = verifyCodeKey;
    }

    private static String verifyCodeKey = VERIFY_CODE_KEY;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        if (request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE) ||
                request.getContentType().startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {


            try {
                ServletInputStream inputStream = request.getInputStream();

                Map userInfo = new ObjectMapper().readValue(inputStream, Map.class);

                String username = (String) userInfo.get(getUsernameParameter());
                String password = (String) userInfo.get(getPasswordParameter());
                String verifyCode = (String) userInfo.get(getVerifyCodeKey());
                String kaptcha = (String) request.getSession(true).getAttribute(KAPTCHA_KEY);

                if (ObjectUtils.isEmpty(userInfo) || ObjectUtils.isEmpty(password)) {
                    throw new MyAuthenticationException("账号/密码不能为空");
                }


                if (ObjectUtils.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(verifyCode)) {
                    throw new MyAuthenticationException("验证码不匹配");
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authenticationToken);

                // 加密方法
//                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//                String encoded = encoder.encode("123");

                try {
                    return this.getAuthenticationManager().authenticate(authenticationToken);
                } catch (BadCredentialsException ex) {
                    throw new MyAuthenticationException("账号/密码不正确");
                } catch (Exception ex) {
                    throw new MyAuthenticationException("认证失败");

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        throw MyBusinessException.fail("认证失败");
    }
}
