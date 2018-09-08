package com.example.medicine.model.expands;

public interface IFailureReason {

    void setCode(int code);

    int getCode();

    void setMessage(String message);

    String getMessage();
}