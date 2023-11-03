package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.FlightsEntity;
import com.example.aiport.entity.RequestsTakingPlaneEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.FailConfirmException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.FlightsEntityRepository;
import com.example.aiport.repository.RequestsTakingPlaneEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.ChiefDispatcherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ChiefDispatcherValidatorImpl implements ChiefDispatcherValidator {
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    @Autowired
    public ChiefDispatcherValidatorImpl(AirPlansEntityRepository airPlansEntityRepository,
                                        FlightsEntityRepository flightsEntityRepository,
                                        RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository,
                                        UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.requestsTakingPlaneEntityRepository = requestsTakingPlaneEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public void validateConfirm(Long id, String answer) throws FailConfirmException {

        if (Objects.isNull(id)){
            throw new FailConfirmException("Пустой id!");
        }
        if ((Objects.isNull(answer)||answer.isEmpty())){
            throw new FailConfirmException("В ответе содержитс null!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            Optional<AirPlansEntity> airPlansEntity = airPlansEntityRepository.findById(id);
            if (airPlansEntity.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer) || PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)) {
                    Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);

                    if (PlaneStatus.SEND.name().equals(plane.get().getStatus())){
                        return;
                    }
                    throw new FailConfirmException("Ваш самолет не совпадает со статусом SEND!");
                }
                throw new FailConfirmException("Вы можете говорить только ACCEPT мли REFUSE!");
            }
            throw new FailConfirmException("Этот самолет принадлежит другому Аэрпорту!");
        }


    }

    @Override
    public void validateConfirmFlights(Long id, String answer) throws FailConfirmException {
        if (Objects.isNull(id)){
            throw new FailConfirmException("Пустой id!");
        }
        if ((Objects.isNull(answer)||answer.isEmpty())){
            throw new FailConfirmException("В ответе содержитс null!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<FlightsEntity> f = flightsEntityRepository.findById(id);
            if (f.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer) || PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)) {
                    Optional<FlightsEntity> flights = flightsEntityRepository.findById(id);

                    if (FlightsStatusEnum.SEND.name().equals(flights.get().getStatus())){
                        return;
                    }
                    throw new FailConfirmException("Ваш рейс не совпадает со статусом SEND!");
                }
                throw new FailConfirmException("Вы можете говорить только ACCEPT мли REFUSE!");
            }
            throw new FailConfirmException("Этот рейс принадлежит другому Аэрпорту!");
        }



    }

    @Override
    public void validateConfirmSendingFlights(Long id, String answer) throws FailConfirmException {
        if (Objects.isNull(id)){
            throw new FailConfirmException("Пустой id!");
        }
        if ((Objects.isNull(answer)||answer.isEmpty())){
            throw new FailConfirmException("В ответе содержитс null!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<FlightsEntity> f = flightsEntityRepository.findById(id);
            if (f.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer) || PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)) {
                    Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                    Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plane.get());
                    if (FlightsStatusEnum.IS_READY.name().equals(flights.get().getStatus())){
                        return;
                    }
                    throw new FailConfirmException("Ваш рейс не совпадает со статусом IS_READY!");
                }
                throw new FailConfirmException("Вы можете говорить только ACCEPT мли REFUSE!");
            }
            throw new FailConfirmException("Этот рейс принадлежит другому аэрпорту");
        }



    }

    @Override
    public void validateConfirmAdoption(Long id) throws FlyException {
        if (Objects.isNull(id)){
            throw new FlyException("Пустой id!");
        }
        for (RequestsTakingPlaneEntity requestsTakingPlaneEntity : requestsTakingPlaneEntityRepository.findAll()){
            if (requestsTakingPlaneEntity.getFlights().getAirPlansEntity().getId().equals(id)){
                return;
            }
        }
        throw new FlyException("id не совпадает!");
    }
}

