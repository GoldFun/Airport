package com.example.aiport.service;

import com.example.aiport.exception.InvalidCredentialsException;
import com.example.aiport.exception.InvalidLoginException;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    public String register(String login, String password, String surname) throws InvalidLoginException;


}
