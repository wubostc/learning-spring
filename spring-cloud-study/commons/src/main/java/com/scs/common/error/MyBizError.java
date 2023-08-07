package com.scs.common.error;

import com.sun.net.httpserver.Authenticator;

public enum MyBizError {
    SUCCESS(1),
    FAILURE(-1),



    PARAMS_VALIDATION_FAILED(1001),

    // 熔断异常
    HYSTRIX_RUNTIME_EXCEPTION(2001),
    // sentinel限流
    FLOW_LIMITING(2002);

    private Integer code;

    MyBizError(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.name();
    }


}
