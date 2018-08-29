package com.taotao.web.httpclient;

/**
 * @Description //TODO Httpclient返回实体类$
 * @Date 18:59
 **/
public class HttpResult {
    /**
     *  响应状态码
     **/
    private Integer code;
    /**
     *  返回内容实体
     **/
    private String data;

    public HttpResult() {
    }

    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
