package com.example.aiport.validators;

import com.example.aiport.exception.InvalidRequestException;

public interface ChiefEngineerValidator {
    public void validateGiveOrder(Long id, String planeStatus) throws InvalidRequestException;
    public void validateSend(Long id) throws InvalidRequestException;
}
