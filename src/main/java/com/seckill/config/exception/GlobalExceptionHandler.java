package com.seckill.config.exception;

import com.seckill.common.ResponseCode;
import com.seckill.common.ServerResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ServerResponse<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if (e instanceof BindException){
            BindException bindException = (BindException)e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);
            return ServerResponse.createByErrorMessage(error.getDefaultMessage());
        }else if (e instanceof GlobalException){
            return ServerResponse.createByErrorCodeMessage(((GlobalException) e).getCode().getCode(),((GlobalException) e).getCode().getDesc());
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.SERVER_ERROR.getCode(),ResponseCode.SERVER_ERROR.getDesc());
    }
}
