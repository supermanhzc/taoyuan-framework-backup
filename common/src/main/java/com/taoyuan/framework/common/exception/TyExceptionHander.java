package com.taoyuan.framework.common.exception;

import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.http.TyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class TyExceptionHander {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TyResponse constraintViolationException(ConstraintViolationException e) {
        TyResponse response = new TyResponse();
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setMsg("校验异常:" + e.getMessage());
        return response;
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TyResponse IllegalArgumentException(IllegalArgumentException e) {
        TyResponse response = new TyResponse();
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setMsg("非法参数异常:" + e.getMessage());
        return response;
    }

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TyResponse noHandlerFoundException(HttpServletRequest requset, Exception ex) {
        TyResponse response = new TyResponse();
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setMsg("请求非法:" + requset.getRequestURI());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(ValidateException.class)
    public TyResponse processValidateException(HttpServletRequest requset, Exception e) {
        log.error("error happend. reason is {}", e.getMessage());
        TyResponse response = new TyResponse();
        ValidateException applicationException = (ValidateException) e;
        response.setCode(String.valueOf(applicationException.getCode()));
        response.setMsg(applicationException.getMessage());
        response.setData(applicationException.getParams());

        return response;
    }

    @ResponseBody
    @ExceptionHandler(TyException.class)
    public TyResponse processTyException(HttpServletRequest requset, TyException e) {
        log.error("error happend. reason is {}", e.getMessage());
        TyResponse response = new TyResponse();
        response.setCode(String.valueOf(e.getCode()));
        response.setMsg("框架异常[" + e.getMessage() + "]");
        return response;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public TyResponse processException(HttpServletRequest requset, Exception e) {
        log.error("error happend. reason is {}", e.getMessage());
        TyResponse response = new TyResponse();
        response.setCode(String.valueOf(ResultCode.UNKONW.getCode()));
        response.setMsg("未知异常[" + e.getMessage() + "]");

        return response;
    }
}
