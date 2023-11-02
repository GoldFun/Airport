package com.example.aiport.controller.advice;

import com.example.aiport.controller.v1.ChiefDispatcherController;
import com.example.aiport.controller.v1.ChiefEngineerController;
import com.example.aiport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ChiefEngineerController.class)
public class ChiefEngineerControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleException(Exception e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
    }

}
