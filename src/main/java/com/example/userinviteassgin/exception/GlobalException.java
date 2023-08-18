package com.example.userinviteassgin.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final String errorCode;

    public GlobalException(Errorcode errorcode){
        super(errorcode.getMESSAGE());
        this.errorCode = errorcode.toString();
    }
}
