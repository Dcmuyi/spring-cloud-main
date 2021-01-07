
package com.muyi.servicestudy.exception;

import com.muyi.servicestudy.utils.Result;
import com.netflix.client.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    public CommonExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AppParamsException.class})
    public Result handleAppParamsEx(AppParamsException ex) {
        LOGGER.error("参数错误异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AppBizException.class})
    public Result handleAppBizEx(AppBizException ex) {
        LOGGER.error("业务处理异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.BIZ_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AppAuthException.class})
    public Result handleAppAuthEx(AppAuthException ex) {
        LOGGER.error("鉴权异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.AUTH_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({AppLoginException.class})
    public Result handleAppLoginEx(AppLoginException ex) {
        LOGGER.error("登录异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.LOGIN_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ClientException.class})
    public Result handleClientEx(ClientException ex) {
        LOGGER.error("客户端异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({NumberFormatException.class})
    public Result handleNumberFormatEx(NumberFormatException ex) {
        LOGGER.error("数字格式化异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), "数字格式错误");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Result handleSpringBindEx(Exception ex) {
        LOGGER.error("spring参数绑定异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), "参数格式错误");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleHttpRequestMethodEx(HttpRequestMethodNotSupportedException ex) {
        LOGGER.error("请求method异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public Result handleServerEx(Exception ex) {
        LOGGER.error("服务器处理异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.SERVER_EX.getCode(), "服务器内部处理异常");
    }
}
