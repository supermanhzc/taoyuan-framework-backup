package com.taoyuan.framework.common.exception;

import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.http.TyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class TyExceptionHander {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public TyResponse processException(Exception e){
        log.error("error happend. reason is {}", e.getMessage());
        Map<String,Object> map = new HashMap<String,Object>();
        TyResponse response = new TyResponse();
        if(e instanceof ValidateException){
            ValidateException applicationException = (ValidateException)e;
            response.setCode(String.valueOf(applicationException.getCode()));
            response.setMsg(applicationException.getMessage());
            response.setData(applicationException.getParams());
        }else if(e instanceof TyException){
            TyException frameworkException = (TyException)e;
            response.setCode(String.valueOf(frameworkException.getCode()));
            response.setMsg("框架异常");
            response.setData(frameworkException.getMessage());
        }else{
            response.setCode(String.valueOf(ResultCode.UNKONW.getCode()));
            response.setMsg("未知异常");
            response.setMsg(e.getMessage());
            response.setData(e.getCause());
        }

        return  response;
    }
}
