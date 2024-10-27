package com.example.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements CodeInterface {
    TEST(-1, "tesr"), 
    REDIS_VALUE_NOT_FOUND(100, "redis valud not found");

    private final Integer code;
    private final String message;
}