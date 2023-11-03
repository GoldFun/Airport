package com.example.aiport.validators;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.ActionOneselfException;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;

public interface AirportManagerValidator {
    public void validateChangeRoleByRole(String role) throws  InvalidRoleException;
    public void validateChangeRoleByLogin(String login, UsersEntity user) throws  InvalidLoginException;
    public void validateRetireByRole(String role) throws InvalidRoleException, InvalidLoginException, ActionOneselfException;
    public void validateRetireByLogin(String login, UsersEntity user) throws  InvalidLoginException;
    public void validateRecruitByAnswer(String answer) throws EmptyAnswerException;
    public void validateRecruitByLogin(String login) throws InvalidLoginException;


}
