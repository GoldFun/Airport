package com.example.aiport.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolesEntityRepository rolesEntityRepository;
    private final AirportEntityRepository airportEntityRepository;
    private final AuthValidator authValidator;


    @Autowired
    public AuthServiceImpl(UsersEntityRepository usersEntityRepository,
                           UserRolesRepository userRolesRepository,
                           RolesEntityRepository rolesEntityRepository,
                           AirportEntityRepository airportEntityRepository,
                           AuthValidator authValidator
    ) {
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolesEntityRepository = rolesEntityRepository;
        this.airportEntityRepository = airportEntityRepository;
        this.authValidator = authValidator;
    }

    @Override
    public String register(String login, String password, String surname) throws InvalidLoginException {
        authValidator.validateRegister(login, password, surname);
        System.out.println("11");
        UsersEntity user = new UsersEntity();
        user.setLogin(login);
        user.setPassword(password);
        user.setSurname(surname);
        usersEntityRepository.save(user);

        RolesEntity roles = rolesEntityRepository.getReferenceById(10L);

        UserRoles userRoles = new UserRoles();
        userRoles.setUsersEntity(user);
        userRoles.setRolesEntity(roles);
        userRolesRepository.save(userRoles);
            return login;

    }


}
