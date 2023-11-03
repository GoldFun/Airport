package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.ChiefStewardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChiefStewardValidatorImpl implements ChiefStewardValidator {
private final UsersEntityRepository usersEntityRepository;
private final AirPlansEntityRepository airPlansEntityRepository;
private final UserRolesRepository userRolesRepository;

    @Autowired
    public ChiefStewardValidatorImpl(UsersEntityRepository usersEntityRepository,
                                     AirPlansEntityRepository airPlansEntityRepository,
                                     UserRolesRepository userRolesRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public void validateScheduleBriefing(UsersEntity user) throws StewardException {
        if (user.getAirPlansEntity().getStatus().equals("FLY")){
            return;
        }
        throw new StewardException("Самолет ещё не взлетел!");
    }

    @Override
    public void validateScheduleGiveFood(UsersEntity user) throws StewardException {
        System.out.println(1232314234);
        if (user.getAirPlansEntity().getStatus().equals("FLY")){
            System.out.println(1234);
            return;
        }
        System.out.println(123);
        throw new StewardException("Самолет ещё не взлетел!");
    }
    @Override
    public AirPlansEntity validateWantPlaneNull(Long id) throws InvalidPlaneException {
        for (AirPlansEntity planes : airPlansEntityRepository.findAll()){
            System.out.println(planes.getId()+" and "+id);
            if (planes.getId().equals(id)){
                System.out.println("верно");
                System.out.println(planes.getId());
                System.out.println(planes.getMarka());
                System.out.println(planes.getModel());
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
                if (user.isEmpty()){
                    return;
                }
                throw new InvalidPlaneException("Этот самолет уже занят!");
            }
        }
        throw new InvalidPlaneException("В данный момент самолет еще не зарегестрирован или недоступен!");
    }
}
