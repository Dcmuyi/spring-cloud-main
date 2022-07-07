
package com.muyi.servicestudy.exception;

import com.muyi.servicestudy.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.stream.Collectors;

@ResponseBody
@ControllerAdvice
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
    @ExceptionHandler({NumberFormatException.class})
    public Result handleNumberFormatEx(NumberFormatException ex) {
        LOGGER.error("数字格式化异常：msg={}", ex.getMessage(), ex);
        return Result.wrapErrorResult(ErrorCode.PARAMS_EX.getCode(), "数字格式错误");
    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Result handleValidatedException(Exception e) {
        LOGGER.error("参数校验失败："+e.getMessage());
        Result resp = null;

        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            resp = Result.wrapErrorResult(HttpStatus.BAD_REQUEST.value(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            resp = Result.wrapErrorResult(HttpStatus.BAD_REQUEST.value(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            resp = Result.wrapErrorResult(HttpStatus.BAD_REQUEST.value(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        }

        return resp;
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
