package com.smartshop.AgenticAi.exception;


public class ReturnNotEligibleException extends RuntimeException {
    public ReturnNotEligibleException(String reason) {
        super(reason);
    }
}