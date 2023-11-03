package com.example.aiport.validators.impl;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AuthValidatorImpl implements AuthValidator {
    private final UsersEntityRepository usersEntityRepository;

    @Autowired
    public AuthValidatorImpl(UsersEntityRepository usersEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public void validateRegister(String login, String password, String surname) throws InvalidLoginException {
        System.out.println("sec");
        if (Objects.isNull(login)||login.isEmpty()){
            throw new InvalidLoginException("В логине содержится null!");
        }
        if (Objects.isNull(password)||password.isEmpty()){
            throw new InvalidLoginException("В пароле содержится null!");
        }
        if (Objects.isNull(surname)||surname.isEmpty()){
            throw new InvalidLoginException("В фамилии содержится null!");
        }

        for (UsersEntity users : usersEntityRepository.findAll()){
            if (users.getLogin().equals(login)){
                throw new InvalidLoginException("Данный логин занят!");
            }
        }
    }
}
