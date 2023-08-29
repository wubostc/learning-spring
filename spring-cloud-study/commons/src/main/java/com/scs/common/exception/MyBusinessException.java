package com.scs.common.exception;

import com.scs.common.error.MyBizError;

public class MyBusinessException extends RuntimeException {
    private Integer code;
    private String msg;
    private String rid;

    public static MyBusinessException fail(Object msg) {
        return MyBusinessException.fail(msg.toString());
    }

    public static MyBusinessException fail(String msg) {
        return new MyBusinessException(-1, msg);
    }

    public static MyBusinessException fail(Integer code, String msg) {
        return new MyBusinessException(code, msg);
    }


    public MyBusinessException(MyBizError e) {
        super("[" +e.getCode() + "] " + e.name());
        this.code = e.getCode();
        this.msg = e.name();
    }

    public MyBusinessException(MyBizError e, String message) {
        super("[" +e.getCode() + "] " + message);
        this.code = e.getCode();
        this.msg = message;
    }

    public MyBusinessException(Integer code, String message) {
        super("[" +code + "] " + message);
        this.code = code;
        this.msg = message;
        this.rid = "";
    }

    public MyBusinessException(Integer code, String message, Throwable cause) {
        super("[" +code + "] " + message, cause);
        this.code = code;
        this.msg = message;
        this.rid = "";
    }

    public MyBusinessException(Integer code, String message, String rid, Throwable cause) {
        super("[" +code + "] " + message, cause);
        this.code = code;
        this.msg = message;
        this.rid = rid;
    }



    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }



    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
