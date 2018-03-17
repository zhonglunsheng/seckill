package com.seckill.config.exception;

import com.seckill.common.ResponseCode;

public class GlobalException extends RuntimeException {
    private ResponseCode code;

    public GlobalException(ResponseCode code){
        super(code.getDesc().toString());
        this.code = code;
    }

    public ResponseCode getCode() {
        return code;
    }
}
