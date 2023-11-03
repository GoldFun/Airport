package com.example.aiport.service.impl;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.UserNotFoundException;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UsersEntityRepository usersEntityRepository;

    @Autowired
    public UserServiceImpl(UsersEntityRepository usersEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public UsersEntity getUserById(Long id) throws UserNotFoundException {
        Optional<UsersEntity> optionalUser = this.usersEntityRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("Данного пользователя с  ID "+id+ " не существует в системе!");
        }
        return optionalUser.get();
    }

    @Override
    public UsersEntity getUserByLogin(String login) throws UserNotFoundException {
        Optional<UsersEntity> optionalUser = usersEntityRepository.findByLogin(login);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException("Данного пользователя с  логином "+login+ " не существует в системе!");
        }
        return optionalUser.get();
    }
}
