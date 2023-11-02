package com.example.aiport.validators.impl;

import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.repository.*;
import com.example.aiport.validators.PilotValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PilotValidatorImpl implements PilotValidator {
    private final RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    @Autowired
    public PilotValidatorImpl(RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository,
                              FlightsEntityRepository flightsEntityRepository,
                              AirPlansEntityRepository airPlansEntityRepository,
                              UsersEntityRepository usersEntityRepository,
                              UserRolesRepository userRolesRepository) {
        this.requestsTakingPlaneEntityRepository = requestsTakingPlaneEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Override
    public void validateTakeOff(UsersEntity user) throws FlyException {
        if (user.getAirPlansEntity().getStatus().equals("FLY")){
        throw new FlyException("Самолет уже взлетел!");
        }
    }

    @Override
    public void validatePlaneLanding(Long id) throws FlyException {
        for (RequestsTakingPlaneEntity r : requestsTakingPlaneEntityRepository.findAll()){
            if (r.getFlights().getId().equals(id)){
                throw new FlyException("вы уже отправили запрос на посадку!");
            }
        }
    }

    @Override
    public void validateLanding(UsersEntity user) throws FlyException {
        Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(user.getAirPlansEntity());
        if (FlightsStatusEnum.CONFIRM_ADOPTION.name().equals(flights.get().getStatus())){
            if (user.getAirPlansEntity().getStatus().equals("FLY")){
                return;
            }
        }



        throw new FlyException("Рейс не соответствует статусу CONFIRM_ADOPTION");
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
