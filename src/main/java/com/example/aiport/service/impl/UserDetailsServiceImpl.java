package com.example.aiport.service.impl;

import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.UserNotFoundException;
import com.example.aiport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsersEntity usersEntity = this.userService.getUserByLogin(username);
            return usersEntity;
        }catch (UserNotFoundException e){
            throw new UsernameNotFoundException("Данного пользователя с  логином "+ username + " не существует в системе!");
        }
    }
}
