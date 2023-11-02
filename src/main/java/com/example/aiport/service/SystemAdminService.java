package com.example.aiport.service;

import com.example.aiport.dto.ReadingDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.ReadingsEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;

import java.util.List;

public interface SystemAdminService {
    public void changeRole(String role, String login) throws InvalidRoleException, InvalidLoginException;
    public List<UserDto> getUserList();
    public List<ReadingDto> getReadings();
}
