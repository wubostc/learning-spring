package com.scs.security.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * 用户认证失败异常
 */
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }
}
