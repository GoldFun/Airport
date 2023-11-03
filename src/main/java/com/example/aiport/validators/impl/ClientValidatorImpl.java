package com.example.aiport.validators.impl;

import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.exception.*;
import com.example.aiport.repository.*;
import com.example.aiport.validators.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ClientValidatorImpl implements ClientValidator {
    private final RolesEntityRepository rolesEntityRepository;
    private final RequestsJobsEntityRepository requestsJobsEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    @Autowired
    public ClientValidatorImpl(RolesEntityRepository rolesEntityRepository,
                               RequestsJobsEntityRepository requestsJobsEntityRepository,
                               UsersEntityRepository usersEntityRepository,
                               FlightsEntityRepository flightsEntityRepository,
                               HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository,
                               AirPlansEntityRepository airPlansEntityRepository) {
        this.rolesEntityRepository = rolesEntityRepository;
        this.requestsJobsEntityRepository = requestsJobsEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.historyPastFlightsEntityRepository = historyPastFlightsEntityRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
    }

    @Override
    public void validateApplyJobByRole(String role) throws InvalidRoleException {
        if (role.equals("CLIENT")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }
        if (role.equals("SYSTEM_ADMIN")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }
        if (role.equals("MANAGER_AIRPORT")) {
            throw new InvalidRoleException("Вы не можете дать ему эту роль!");
        }
        if (Objects.isNull(role)||role.isEmpty()){
            throw new InvalidRoleException("Пустая роль!");
        }
        for (RolesEntity roles : rolesEntityRepository.findAll()) {
            if (roles.getTitle().equals(role)) {
                return;
            }
        }
        throw new InvalidRoleException("Данной роли не существует!");
    }

    @Override
    public void validateApplyJobByMessage(String message) throws EmptyMessageException {
    if (Objects.isNull(message)||message.isEmpty()){
        throw new EmptyMessageException("Добавьте хоть какую то информацию про себя!");
    }

    }

    @Override
    public void validateApplyJobsByLogin(String login) throws InvalidLoginException {
        for (RequestsJobsEntity requestsJobs : requestsJobsEntityRepository.findAll()){
            if (requestsJobs.getLogin().equals(login)){
                throw new InvalidLoginException("Вы уже отправили запрос!");
            }
        }
    }

    @Override
    public void validateRegisterFlights(Long id) throws InvalidFlightsException {
        if(Objects.isNull(id)){
            throw new InvalidFlightsException("Пустой id!");
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            if (Objects.isNull(authenticationUser.get().getFlightsEntity())){
                for (FlightsEntity flights : flightsEntityRepository.findAll()){
                    if (flights.getId().equals(id)){
                        return;
                    }
                }
                throw new InvalidFlightsException("Такого рейса с таким id нету!");

            }
            throw new InvalidFlightsException("Вы уже зарегесрировались на рейс!");
        }
    }

    @Override
    public void validateLogoutFlights(Long id) throws LogOutException {
        Optional <UsersEntity> user = usersEntityRepository.findById(id);
        if (Objects.isNull(user.get().getFlightsEntity())){
            throw new LogOutException("Вы еще не зарегестрировались на рейс чтобы из него выйти!");
        }
        if (FlightsStatusEnum.IS_BUSY.name().equals(user.get().getFlightsEntity().getStatus())){
            throw new LogOutException("Извините но уже слишком поздно для выхода из рейса!");
        }

    }

    @Override
    public Optional<FlightsEntity> validateReviews(Long id, String message) throws ReviewsException {
        if(Objects.isNull(id)){
            throw new ReviewsException("Пустой id!");
        }
        if(Objects.isNull(message)||message.isEmpty()){
            throw new ReviewsException("Пустое сообщение!");
        }
            Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
            Optional<FlightsEntity> flightsEntity = flightsEntityRepository.findByAirPlansEntity(plane.get());
                for (AirPlansEntity plans : airPlansEntityRepository.findAll()){
                    if (plans.getId().equals(id)){
                        System.out.println(123);

                        for (HistoryPastFlightsEntity history : historyPastFlightsEntityRepository.findAll()){
                            if (history.getFlights().equals(flightsEntity.get())){
                                return flightsEntity;
                            }
                        }
                    }
                }
        throw new ReviewsException("рейса с таким id нету!");


    }
}
