package com.crawler.utils;

/**
 * 返回结果
 */
public class ReturnResult {
    private String code;
    private String msg;
    private Object data;

    public ReturnResult() {
        setCode("0");
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}