package com.example.aiport.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.example.aiport.entity.RolesEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.repository.AirportEntityRepository;
import com.example.aiport.repository.RolesEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.AuthService;
import com.example.aiport.validators.AuthValidator;
import com.example.aiport.validators.impl.AuthValidatorImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Spy
    private  UsersEntityRepository usersEntityRepository;
    @Spy
    private  UserRolesRepository userRolesRepository;
    @Spy
    private  RolesEntityRepository rolesEntityRepository;
    @Spy
    private  AirportEntityRepository airportEntityRepository;
    @Spy
    private  AuthValidator authValidator;

    @Test
    public void test_register_OK() throws InvalidLoginException {
        AuthService authService = new AuthServiceImpl(this.usersEntityRepository,this.userRolesRepository,this.rolesEntityRepository,this.airportEntityRepository,authValidator);
        String login = "test";
        String password = "password";
        String surname = "surname";
        String result = authService.register(login,password,surname);
        Assertions.assertEquals(result,login);
    }

    @Test
    public void test_register_Exception() throws InvalidLoginException {
        authValidator = new AuthValidatorImpl(this.usersEntityRepository);
        AuthService authService = new AuthServiceImpl(this.usersEntityRepository,this.userRolesRepository,this.rolesEntityRepository,this.airportEntityRepository,this.authValidator);
        String login = null;
        String password = "password";
        String surname = "surname";
        Assertions.assertThrows(InvalidLoginException.class, ()-> authService.register(login,password,surname));
    }
}

