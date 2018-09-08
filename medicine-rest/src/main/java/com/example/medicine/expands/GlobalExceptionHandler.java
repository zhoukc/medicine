package com.example.medicine.expands;

import com.example.medicine.exception.BusinessException;
import com.example.medicine.features.PublicFailureReasons;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 全局异常处理
 * create by zhoukc
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map parameterError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map map = new HashMap();
        map.put("code", PublicFailureReasons.PARAMETER_ERROR.getIndex());
        map.put("msg", bindingResult.getAllErrors().get(0).getDefaultMessage());
        return map;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map businessException(BusinessException e) {
        Map map = new HashMap();
        map.put("code", e.getCode());
        map.put("msg", e.getMessage());
        return map;
    }

}
