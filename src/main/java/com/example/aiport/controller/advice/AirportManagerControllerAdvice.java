package com.example.aiport.controller.advice;

import com.example.aiport.controller.v1.AirportManagerController;
import com.example.aiport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AirportManagerController.class)
public class AirportManagerControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse handleChangeRole(Exception e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }

}
