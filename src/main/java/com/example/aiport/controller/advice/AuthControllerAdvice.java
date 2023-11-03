package com.example.aiport.controller.advice;

import com.example.aiport.controller.v1.AuthController;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AuthController.class)
public class AuthControllerAdvice {
    @ExceptionHandler(value = InvalidLoginException.class)
    public ErrorResponse handleInvalidLoginArgument(InvalidLoginException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
    }

}
