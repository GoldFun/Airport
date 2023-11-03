package com.example.aiport.validators;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;

public interface SystemAdminValidator {
    public void validateChangeRoleByRole(String role) throws InvalidRoleException;
    public void validateChangeRoleByLogin(String login, UsersEntity user) throws InvalidLoginException;
}
