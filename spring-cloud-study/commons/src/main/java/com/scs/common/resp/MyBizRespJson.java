package com.scs.common.resp;

import com.scs.common.error.MyBizError;

import java.util.HashMap;
import java.util.Map;

public class MyBizRespJson implements MyBizResp {

    static public Object success(Object object) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", MyBizError.SUCCESS.getCode());
        result.put("data", object);
        result.put("traceId", "111");
        return result;
    }

    static public Object fail(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", MyBizError.FAILURE.getCode());
        result.put("msg", msg);
        result.put("traceId", "111");
        return result;
    }
}
