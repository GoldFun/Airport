package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.AirportEntity;
import com.example.aiport.entity.FlightsEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.*;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.AirportEntityRepository;
import com.example.aiport.repository.FlightsEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.DispatcherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class DispatcherValidatorImpl implements DispatcherValidator {
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final AirportEntityRepository airportEntityRepository;
    private final UsersEntityRepository usersEntityRepository;

    @Autowired
    public DispatcherValidatorImpl(AirPlansEntityRepository airPlansEntityRepository,
                                   FlightsEntityRepository flightsEntityRepository,
                                   AirportEntityRepository airportEntityRepository,
                                   UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.airportEntityRepository = airportEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public void validateCreatePlane(String model, String marka, Integer countPlace) throws InvalidPlaneException {

        if (Objects.isNull(model) || model.isEmpty()) {
            throw new InvalidPlaneException("У самолета нету модели!");
        }
        if (Objects.isNull(marka) || marka.isEmpty()) {
            throw new InvalidPlaneException("У самолета нету марки!");
        }
        if ((Objects.isNull(countPlace))) {
            throw new InvalidPlaneException("У самолета нету места для пассажиров!");
        }
        if (countPlace >= 350) {
            throw new InvalidPlaneException("В самолете слишком много мест!");
        }
        if (countPlace <= 3) {
            throw new InvalidPlaneException("В самолете слишком мало мест!");
        }
    }

    @Override
    public void validateSendChiefDispatcher(Long id) throws FailSendException {
        if (Objects.isNull(id)) {
            throw new FailSendException("Пустой id!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);
            if (airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                if (PlaneStatus.NORMAL.name().equals(plane.get().getStatus())) {
                    return;
                }
                throw new FailSendException("Самолет не соответствует статусу NORMAL!");
            }
            throw new FailSendException("Этот самолет принадлежит другому Аэрпорту!");
        }


    }

    @Override
    public AirPlansEntity validateSendChiefDispatcherFlights(Long id) throws FailSendException {
        if (Objects.isNull(id)) {
            throw new FailSendException("Пустой id!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);
            if (airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);

                if (PlaneStatus.REFILLED.name().equals(plane.get().getStatus())) {
                    return plane.get();
                }
                throw new FailSendException("Самолет не заправлен!");
            }
            throw new FailSendException("Этот рейс принадлежит другому Аэрпорту!");
        }
        return null;
    }



    @Override
    public void validateAcceptTakingPlane(Long id) throws FlyException {
        if (Objects.isNull(id)){
            throw new FlyException("Пустой id!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<FlightsEntity> f = flightsEntityRepository.findById(id);
            if (f.get().getPlaceOfDeparture().getId().equals(authenticationUser.get().getAirportEntity().getId())){
                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plane.get());
                if (plane.get().getStatus().equals("FLY")){
                    if (flights.get().getStatus().equals("ADOPTION")){
                        throw new FlyException("Вы уже отдали главному диспечтеру!");
                    }
                    return;
                }
                throw new FlyException("Самолет не соответствует статусу FLY!");
            }
        }
        throw new FlyException("Этот рейс не давал запрос на посадку!");


    }

    @Override
    public void validateCreateFlights(String title, Long placeOfDeparture, Long id) throws InvalidFlightsException {
    if (Objects.isNull(title)||title.isEmpty()){
        throw new InvalidFlightsException("Пустое название!");
    }
    if (Objects.isNull(placeOfDeparture)){
        throw new InvalidFlightsException("Пустая точка назначение!");
    }
    if (Objects.isNull(id)){
        throw new InvalidFlightsException("Пустой id самолета!");
    }



    for (AirPlansEntity planes : airPlansEntityRepository.findAll()){
        if (planes.getId().equals(id)){
            Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
            if (PlaneStatus.CONFIRM.name().equals(plane.get().getStatus())){

                for (AirportEntity airport : airportEntityRepository.findAll()){
                    if (airport.getId().equals(placeOfDeparture)){
                        return;
                    }
                }
                throw new InvalidFlightsException("у нас нет аэрпотра с таким id!");

            }
            throw new InvalidFlightsException("Статус не совпадает!");
        }
    }
    throw new InvalidFlightsException("такого id в самолетах нету!");
    }


}
