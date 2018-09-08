package com.example.medicine.exception;

public class BusinessException extends RuntimeException {

    private String message;
    private Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
