package com.example.aiport.service;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.UserNotFoundException;

public interface UserService {
    public UsersEntity getUserById(Long id) throws UserNotFoundException;
    public UsersEntity getUserByLogin(String login) throws UserNotFoundException;
}
