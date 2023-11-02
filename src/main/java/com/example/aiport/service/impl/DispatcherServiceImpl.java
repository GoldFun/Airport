package com.example.aiport.service.impl;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.*;
import com.example.aiport.repository.*;
import com.example.aiport.service.DispatcherService;
import com.example.aiport.validators.DispatcherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class DispatcherServiceImpl implements DispatcherService {
    private final UsersEntityRepository usersEntityRepository;
    private final DispatcherValidator dispatcherValidator;
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final AirportEntityRepository airportEntityRepository;
    private final RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository;

    @Autowired
    public DispatcherServiceImpl(UsersEntityRepository usersEntityRepository,
                                 DispatcherValidator dispatcherValidator,
                                 AirPlansEntityRepository airPlansEntityRepository,
                                 FlightsEntityRepository flightsEntityRepository,
                                 AirportEntityRepository airportEntityRepository,
                                 RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
        this.dispatcherValidator = dispatcherValidator;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.airportEntityRepository = airportEntityRepository;
        this.requestsTakingPlaneEntityRepository = requestsTakingPlaneEntityRepository;
    }

    @Override
    public void createPlane(String model, String marka, Integer countPlace) throws InvalidPlaneException {
        dispatcherValidator.validateCreatePlane(model, marka, countPlace);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            AirPlansEntity plane = new AirPlansEntity();
            plane.setModel(model);
            plane.setMarka(marka);
            plane.setCountPlace(countPlace);
            plane.setAirportEntity(authenticationUser.get().getAirportEntity());

            airPlansEntityRepository.save(plane);
        }
    }

    @Override
    public List<PlaneStatusDto> getPlanes(String filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<PlaneStatusDto> planeList = new ArrayList<>();
            if (!Objects.isNull(filter)) {
                if (filter.equalsIgnoreCase("confirm")) {
                    for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {

                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        PlaneStatus planeStatus = PlaneStatus.valueOf(planes.getStatus());
                        if (PlaneStatus.CONFIRM.name().equals(planes.getStatus())) {
                            if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                                planeStatusDto.setPlaneStatus(planeStatus);
                                planeStatusDto.setMarka(planes.getMarka());
                                planeStatusDto.setCountPlace(planes.getCountPlace());
                                planeStatusDto.setModel(planes.getModel());
                                planeStatusDto.setId(planes.getId());
                                planeList.add(planeStatusDto);
                            }
                        }
                    }
                    return planeList;
                }
                if (filter.equalsIgnoreCase("refilled")) {
                    for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        PlaneStatus planeStatus = PlaneStatus.valueOf(planes.getStatus());
                        if (PlaneStatus.REFILLED.name().equals(planes.getStatus())) {
                            if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                                planeStatusDto.setPlaneStatus(planeStatus);
                                planeStatusDto.setMarka(planes.getMarka());
                                planeStatusDto.setCountPlace(planes.getCountPlace());
                                planeStatusDto.setModel(planes.getModel());
                                planeStatusDto.setId(planes.getId());
                                planeList.add(planeStatusDto);
                            }

                        }
                    }
                    return planeList;
                }

            }
            for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                if (PlaneStatus.NORMAL.name().equals(planes.getStatus())) {
                    if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                        PlaneStatus planeStatus = PlaneStatus.valueOf(planes.getStatus());
                        planeStatusDto.setPlaneStatus(planeStatus);
                        planeStatusDto.setMarka(planes.getMarka());
                        planeStatusDto.setCountPlace(planes.getCountPlace());
                        planeStatusDto.setModel(planes.getModel());
                        planeStatusDto.setId(planes.getId());
                        planeList.add(planeStatusDto);
                    }

                }
            }
            return planeList;
        }
        return null;
    }




    @Override
    public List<RequestTakingPlaneDto> getTakingPlane() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<RequestTakingPlaneDto> list = new ArrayList<>();
            for (RequestsTakingPlaneEntity taking : requestsTakingPlaneEntityRepository.findAll()) {
                RequestTakingPlaneDto requestTakingPlaneDto = new RequestTakingPlaneDto();
                if (taking.getFlights().getPlaceOfDeparture().getId().equals(authenticationUser.get().getAirportEntity().getId())){
                    requestTakingPlaneDto.setIdFlights(taking.getFlights().getAirPlansEntity().getId());
                    requestTakingPlaneDto.setModelPlane(taking.getModelPlane());
                    requestTakingPlaneDto.setAirport(taking.getAirportEntity().getTitle());
                    requestTakingPlaneDto.setPlaceOfDeparture(taking.getPlaceOfDeparture().getTitle());
                    list.add(requestTakingPlaneDto);
                }

            }
            return list;
        }
    return null;
    }

    @Override
    public void acceptTakingPlane(Long id) throws FlyException {
        dispatcherValidator.validateAcceptTakingPlane(id);

        Optional<AirPlansEntity> plans = airPlansEntityRepository.findById(id);
        Optional <FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plans.get());
        flights.get().setStatus("ADOPTION");
        flightsEntityRepository.save(flights.get());

    }


    @Override
    public void sendChiefDispatcher(Long id) throws FailSendException {
        dispatcherValidator.validateSendChiefDispatcher(id);
        Optional<AirPlansEntity> plane =airPlansEntityRepository.findById(id);
        plane.get().setStatus("SEND");
        airPlansEntityRepository.save(plane.get());
    }

    @Override
    public void sendChiefDispatcherFlights(Long id) throws FailSendException {
        AirPlansEntity plane = dispatcherValidator.validateSendChiefDispatcherFlights(id);

        Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plane);


        flights.get().setStatus("IS_READY");
        flightsEntityRepository.save(flights.get());
    }

    @Override
    public void createFlights(String title, Long placeOfDeparture, Long id) throws InvalidFlightsException {
        dispatcherValidator.validateCreateFlights(title, placeOfDeparture, id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            Optional<AirportEntity> airports = airportEntityRepository.findById(placeOfDeparture);

            FlightsEntity flights = new FlightsEntity();
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);
            flights.setTitle(title);
            flights.setPlaceOfDeparture(airports.get());
            flights.setAirPlansEntity(airPlans.get());
            flights.setAirportEntity(authenticationUser.get().getAirportEntity());
            System.out.println(1);
            System.out.println(airPlans.get().getId());
            //flights.setId(airPlans.get().getId());
            System.out.println(2);
            flights.setStatus("SEND");
            flightsEntityRepository.save(flights);
        }
    }
    @Override
    public List<FlightsDto> getConfirmFlights() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<FlightsDto> flightsDtoList = new ArrayList<>();
            for (FlightsEntity flights : flightsEntityRepository.findAll()) {
                FlightsDto flightsDto = new FlightsDto();
                FlightsStatusEnum flightsStatusEnum = FlightsStatusEnum.valueOf(flights.getStatus());
                if (FlightsStatusEnum.CONFIRM.equals(flightsStatusEnum)) {
                    if (flights.getAirportEntity().equals(authenticationUser.get().getAirportEntity())){
                        flightsDto.setId(flights.getId());
                        flightsDto.setTitle(flights.getTitle());
                        flightsDto.setPlaceOfDeparture(flights.getPlaceOfDeparture().getId());
                        flightsDtoList.add(flightsDto);
                    }

                }
            }
            return flightsDtoList;
        }
        return null;
    }


}

