package com.example.aiport.validators;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;

public interface PilotValidator {
    public void validateTakeOff(UsersEntity user) throws FlyException;
    public void validatePlaneLanding(Long id) throws FlyException;
    public void validateLanding(UsersEntity users) throws FlyException;
    public AirPlansEntity validateWantPlaneNull(Long id) throws InvalidPlaneException;
    public void validateWantPlane(Long id, AirPlansEntity plane) throws InvalidPlaneException;
}
