package com.example.aiport.validators.impl;

import com.example.aiport.entity.RolesEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.repository.RolesEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.SystemAdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SystemAdminValidatorImpl implements SystemAdminValidator {
    private final RolesEntityRepository rolesEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    @Autowired
    public SystemAdminValidatorImpl(RolesEntityRepository rolesEntityRepository,
                                    UsersEntityRepository usersEntityRepository
    ) {
        this.rolesEntityRepository = rolesEntityRepository;
        this.usersEntityRepository = usersEntityRepository;

    }

    @Override
    public void validateChangeRoleByRole(String role) throws InvalidRoleException {
        if (Objects.isNull(role) || role.isEmpty()) {
            throw new InvalidRoleException("Пустая роль!");
        }

        if (role.equals("CLIENT")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }
        if (role.equals("SYSTEM_ADMIN")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }

        for (RolesEntity roles : rolesEntityRepository.findAll()) {
            if (roles.getTitle().equals(role)) {
                return;
            }
        }
        throw new InvalidRoleException("Данной роли не существует!");
    }

    @Override
    public void validateChangeRoleByLogin(String login, UsersEntity user) throws InvalidLoginException {
        if (Objects.isNull(login) || login.isEmpty()) {
            throw new InvalidLoginException("В логине содержится null!");
        }
        if (user.getLogin().equals(login)){
            throw new InvalidLoginException("Вы не можете поменять роль самому себе!");
        }

        for (UsersEntity users : usersEntityRepository.findAll()) {
            if (users.getLogin().equals(login)) {
                return;
            }
        }
        throw new InvalidLoginException("Данного пользователя не существует в системе");
    }
}
