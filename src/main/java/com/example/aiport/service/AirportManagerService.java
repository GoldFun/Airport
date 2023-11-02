package com.example.aiport.service;

import com.example.aiport.dto.ChangeRoleDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.RequestsJobsEntity;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.ActionOneselfException;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;

import java.util.List;

public interface AirportManagerService {
    public ChangeRoleDto changeRole(String login, String role) throws InvalidLoginException, InvalidRoleException;
    public void retire(String login) throws InvalidRoleException, InvalidLoginException, ActionOneselfException;
    public List<RequestsJobsEntity> getRequestJobsList();
    public PromoltionEnum recruit(String login, String answer) throws EmptyAnswerException, InvalidLoginException;
    public List<UserDto> getUserList();
    public void reading();
}
