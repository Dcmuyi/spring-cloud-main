package com.muyi.servicestudy.exception;

public enum ErrorCode {

    AUTH_EX(101), PARAMS_EX(102), BIZ_EX(103), SERVER_EX(105), LOGIN_EX(1010);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    private ErrorCode(Integer code){
        this.code = code;
    }
}
