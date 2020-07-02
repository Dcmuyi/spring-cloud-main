package com.muyi.servicestudy.utils;

public class Result<T> {
    private String message = "success";
    private Integer status;
    private T data;

    private Result() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static <T> Result<T> wrapSuccessfulResult(T data) {
        Result<T> result = new Result();
        result.data = data;
        result.status = 200;
        return result;
    }

    public static <T> Result<T> wrapErrorResult(Integer status, String message) {
        Result<T> result = new Result();
        result.status = status;
        result.message = message;
        return result;
    }

    public String toString() {
        return "Result{message='" + this.message + '\'' + ", status='" + this.status + '\'' + ", data=" + this.data + '}';
    }
}