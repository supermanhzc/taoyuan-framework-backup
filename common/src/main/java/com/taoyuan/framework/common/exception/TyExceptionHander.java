package com.taoyuan.framework.common.exception;

import com.taoyuan.framework.common.http.TyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TyExceptionHander {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public TyResponse processException(Exception e){
        log.error("error happend. reason is {}", e.getMessage());
        TyResponse response = new TyResponse();
        //TODO EXCEPTION DEFINE
        response.setCode("220101");
        response.setMsg(e.getMessage());
        return response;
    }
}
