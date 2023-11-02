package com.example.aiport.controller.advice;

import com.example.aiport.controller.v1.EngineerController;
import com.example.aiport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = EngineerController.class)
public class EngineerControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleException(Exception e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
    }
}
