package com.example.aiport.validators;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;

public interface StewardValidator {
    public UsersEntity validateBriefing() throws StewardException;
    public UsersEntity validateGiveFood() throws StewardException;
    public AirPlansEntity validateWantPlaneNull(Long id) throws InvalidPlaneException;
    public void validateWantPlane(Long id, AirPlansEntity plane) throws InvalidPlaneException;
}
