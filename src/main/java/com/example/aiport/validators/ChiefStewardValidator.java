package com.example.aiport.validators;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;

public interface ChiefStewardValidator {
    public void validateScheduleBriefing(UsersEntity user) throws StewardException;
    public void validateScheduleGiveFood(UsersEntity user) throws StewardException;
    public AirPlansEntity validateWantPlaneNull(Long id) throws InvalidPlaneException;
    public void validateWantPlane(Long id, AirPlansEntity plane) throws InvalidPlaneException;
}
