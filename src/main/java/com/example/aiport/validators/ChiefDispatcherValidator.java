package com.example.aiport.validators;

import com.example.aiport.exception.FailConfirmException;
import com.example.aiport.exception.FlyException;

public interface ChiefDispatcherValidator {
    public void validateConfirm(Long id, String answer) throws FailConfirmException;
    public void validateConfirmFlights(Long id, String answer) throws FailConfirmException;
    public void validateConfirmSendingFlights(Long id,String answer) throws FailConfirmException;
    public void validateConfirmAdoption(Long id) throws FlyException;
}
