package com.example.aiport.validators.impl;

import com.example.aiport.entity.RequestsJobsEntity;
import com.example.aiport.entity.RolesEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.ActionOneselfException;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.repository.RequestsJobsEntityRepository;
import com.example.aiport.repository.RolesEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.AirportManagerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AirportManagerValidatorImpl implements AirportManagerValidator {
    private final UsersEntityRepository usersEntityRepository;
    private final RolesEntityRepository rolesEntityRepository;
    private final RequestsJobsEntityRepository requestsJobsEntityRepository;

    @Autowired
    public AirportManagerValidatorImpl(UsersEntityRepository usersEntityRepository,
                                       RolesEntityRepository rolesEntityRepository,
                                       RequestsJobsEntityRepository requestsJobsEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.rolesEntityRepository = rolesEntityRepository;
        this.requestsJobsEntityRepository = requestsJobsEntityRepository;
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
        if (role.equals("MANAGER_AIRPORT")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }

        for (RolesEntity roles : rolesEntityRepository.findAll()) {
            if (roles.getTitle().equalsIgnoreCase(role)) {
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
        throw new InvalidLoginException("Данного пользователя не существует в системе!");
    }

    @Override
    public void validateRetireByRole(String role) throws InvalidRoleException, ActionOneselfException {
        if (role.equals("CLIENT")) {
            throw new InvalidRoleException("Вы не можете уволить пользователя с этой ролью!");
        }
        if (role.equals("SYSTEM_ADMIN")) {
            throw new InvalidRoleException("Вы не можете уволить пользователя с этой ролью!");
        }
        if (role.equals("MANAGER_AIRPORT")) {
            throw new InvalidRoleException("Вы не можете уволить пользователя с этой ролью!");
        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication.getPrincipal() instanceof UserDetails) {
//            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
//
//
//            List<UsersEntity> managersAirportList = usersEntityRepository.findManagersAirport();
//            for (UsersEntity managers : managersAirportList) {
//                if (managers.getLogin().equals(authenticationUser.get().getLogin())){
//                    throw new ActionOneselfException("Нельзя уволить самого себя!");
//                }
//
//            }
//        }
    }

    @Override
    public void validateRetireByLogin(String login, UsersEntity user) throws InvalidLoginException {
        if (Objects.isNull(login) || login.isEmpty()) {
            throw new InvalidLoginException("В логине содержится null!");
        }
        if (user.getLogin().equals(login)){
            throw new InvalidLoginException("Вы не можете уволить самого себя!");
        }

        for (UsersEntity users : usersEntityRepository.findAll()) {
            if (users.getLogin().equals(login)) {
                return;
            }
        }
        throw new InvalidLoginException("Данного пользователя не существует в системе");
    }

    @Override
    public void validateRecruitByAnswer(String answer) throws EmptyAnswerException {
        if (Objects.isNull(answer)||answer.isEmpty()){
            throw new EmptyAnswerException("Вы должны дать свой ответ!");
        }
        if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer)||PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)){
            return;
        }
        throw new EmptyAnswerException("Вы можете говорить только ACCEPT мли REFUSE!");
        }

    @Override
    public void validateRecruitByLogin(String login) throws InvalidLoginException {
        if (Objects.isNull(login)||login.isEmpty()){
            throw new InvalidLoginException("Пустой логин!");
        }
        for (RequestsJobsEntity requestsJobs : requestsJobsEntityRepository.findAll()){
            if (requestsJobs.getLogin().equals(login)){
                return;
            }
        }
        throw new InvalidLoginException("Запроса от этого пользователя нету!");
    }




}


