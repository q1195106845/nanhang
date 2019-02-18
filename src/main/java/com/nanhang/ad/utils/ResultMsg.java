package com.nanhang.ad.utils;

import lombok.Builder;

/**
 * @Author yangjianghe
 * @Date 2018/10/20 10:19
 **/
public class ResultMsg<T> {
    /**
     * 成功：0 失败：1
     */
    private String code;

    private T context;

    private String msg;

    public static ResultMsg success(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setCode("0");
        resultMsg.setMsg(msg);
        return resultMsg;
    }

    public static ResultMsg error(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setCode("1");
        resultMsg.setMsg(msg);
        return resultMsg;
    }

    public static <T> ResultMsg<T> success(T data,String msg){
        ResultMsg<T> resultMsg = new ResultMsg<T>();
        resultMsg.setContext(data);
        resultMsg.setMsg(msg);
        resultMsg.setCode("0");
        return resultMsg;
    }

    public static <T> ResultMsg<T> error(T data,String msg){
        ResultMsg<T> resultMsg = new ResultMsg<T>();
        resultMsg.setContext(data);
        resultMsg.setMsg(msg);
        resultMsg.setCode("1");
        return resultMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
