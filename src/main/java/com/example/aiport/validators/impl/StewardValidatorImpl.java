package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.StewardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StewardValidatorImpl implements StewardValidator {
    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    @Autowired
    public StewardValidatorImpl(UsersEntityRepository usersEntityRepository, UserRolesRepository userRolesRepository,
                                AirPlansEntityRepository airPlansEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
    }

    @Override
    public UsersEntity validateBriefing() throws StewardException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(authenticationUser.get());
            if(userRoles.get().getStatus().equals("BRIEFING")){
                return authenticationUser.get();
            }
            throw new StewardException("Гланвый стюард ещё не распоряжлся такими заданиями!");
        }
        return null;
    }

    @Override
    public UsersEntity validateGiveFood() throws StewardException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(authenticationUser.get());
            if(userRoles.get().getStatus().equals("GIVE_FOOD")){
                return authenticationUser.get();
            }
            throw new StewardException("Гланвый стюард ещё не распоряжлся такими заданиями!");
        }
        return null;
    }
    @Override
    public AirPlansEntity validateWantPlaneNull(Long id) throws InvalidPlaneException {
        for (AirPlansEntity planes : airPlansEntityRepository.findAll()){
            if (planes.getId().equals(id)){
                return planes;
            }
        }
        throw new InvalidPlaneException("Самолёта с таким id не существует!");

    }

    @Override
    public void validateWantPlane(Long id, AirPlansEntity plane) throws InvalidPlaneException {

        if (plane.getStatus().equals("CONFIRM")){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof UserDetails) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

                Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(authenticationUser.get());
                Optional<AirPlansEntity> airPlansEntity = airPlansEntityRepository.findById(id);
                List<UsersEntity> user = usersEntityRepository.findByRolesIdAndAirplanes(userRoles.get().getRolesEntity().getId(),airPlansEntity.get().getId());
                for (UsersEntity users : user){
                    System.out.println(users.getLogin());
                    System.out.println(user.size());
                }
                if (user.isEmpty() || user.size() == 2 || user.size() == 1){
                    return;
                }
                throw new InvalidPlaneException("Этот самолет уже занят!");
            }



        }
        throw new InvalidPlaneException("В данный момент самолет еще не зарегестрирован или недоступен!");
    }
}
