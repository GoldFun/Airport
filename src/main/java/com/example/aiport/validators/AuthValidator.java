package com.example.aiport.validators;

import com.example.aiport.exception.InvalidLoginException;

public interface AuthValidator {
    public void validateRegister(String login, String password, String surname) throws InvalidLoginException;
}
