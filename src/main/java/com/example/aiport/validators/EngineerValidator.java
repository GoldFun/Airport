package com.example.aiport.validators;

import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;

public interface EngineerValidator {
    public void validateTechReview(Long id) throws FailTechReviewException;
    public void validateRepair(Long id) throws FailRepairException;
    public void validateRefuel(Long id) throws FailRefuelException;
}
