package com.example.aiport.service.impl;


import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
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
import com.example.aiport.service.ChiefDispatcherService;
import com.example.aiport.validators.ChiefDispatcherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChiefDispatcherServiceImpl implements ChiefDispatcherService {
    private final ChiefDispatcherValidator chiefDispatcherValidator;
    private final FlightsEntityRepository flightsEntityRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final UsersEntityRepository usersEntityRepository;
    private final RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository;
    @Autowired
    public ChiefDispatcherServiceImpl(ChiefDispatcherValidator chiefDispatcherValidator,
                                      FlightsEntityRepository flightsEntityRepository,
                                      AirPlansEntityRepository airPlansEntityRepository,
                                      UsersEntityRepository usersEntityRepository,
                                      RequestsTakingPlaneEntityRepository requestsTakingPlaneEntityRepository) {
        this.chiefDispatcherValidator = chiefDispatcherValidator;
        this.flightsEntityRepository = flightsEntityRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
        this.requestsTakingPlaneEntityRepository = requestsTakingPlaneEntityRepository;
    }


    @Override
    public List<PlaneStatusDto> getPlanes(String filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<PlaneStatusDto> planeList = new ArrayList<>();
            if (!Objects.isNull(filter)) {
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
            for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                if (PlaneStatus.SEND.name().equals(planes.getStatus())) {
                    if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
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
    public PromoltionEnum confirm(Long id, String answer) throws FailConfirmException {
    chiefDispatcherValidator.validateConfirm(id, answer);

    if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer)){
        Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
        plane.get().setStatus("CONFIRM");
        airPlansEntityRepository.save(plane.get());
        return PromoltionEnum.ACCEPT;
    }
    if (PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)){
        Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
        airPlansEntityRepository.delete(plane.get());
        return PromoltionEnum.REFUSE;
    }
    return null;
    }



    @Override
    public PromoltionEnum confirmFlights(Long id, String answer) throws FailConfirmException {
        chiefDispatcherValidator.validateConfirmFlights(id,answer);

        if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer)){
            Optional<FlightsEntity> flights = flightsEntityRepository.findById(id);
            flights.get().setStatus("CONFIRM");
            flightsEntityRepository.save(flights.get());
            return PromoltionEnum.ACCEPT;
        }
        if (PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)){
            Optional<FlightsEntity> flights = flightsEntityRepository.findById(id);
            flightsEntityRepository.delete(flights.get());
            return PromoltionEnum.REFUSE;
        }
        return null;
    }
    @Override
    public PromoltionEnum confirmSendingFlights(Long id, String answer) throws FailConfirmException {
        chiefDispatcherValidator.validateConfirmSendingFlights(id,answer);
        Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
        Optional<FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plane.get());

        if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer)){
            flights.get().setStatus("CONFIRMED_FLIGHTS");
            flightsEntityRepository.save(flights.get());
            return PromoltionEnum.ACCEPT;
        }
        if (PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)){
            List<UsersEntity> userList = usersEntityRepository.findUsersByFlights(id);
            for (UsersEntity users : userList){
                users.setFlightsEntity(null);
                usersEntityRepository.save(users);
            }
            flights.get().setStatus("CONFIRM");
            flightsEntityRepository.save(flights.get());
            return PromoltionEnum.REFUSE;
        }
        return null;
    }

    @Override
    public List<FlightsDto> getConfirmFlights(String filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<FlightsDto> flightsDtoList = new ArrayList<>();
            if (!Objects.isNull(filter)) {
                if (filter.equalsIgnoreCase("send")) {
                    for (FlightsEntity flights : flightsEntityRepository.findAll()) {
                        FlightsDto flightsDto = new FlightsDto();
                        FlightsStatusEnum flightsStatusEnum = FlightsStatusEnum.valueOf(flights.getStatus());
                        if (FlightsStatusEnum.SEND.equals(flightsStatusEnum)) {
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
                if (filter.equalsIgnoreCase("takeFly")) {
                    for (FlightsEntity flights : flightsEntityRepository.findAll()) {
                        FlightsDto flightsDto = new FlightsDto();
                        FlightsStatusEnum flightsStatusEnum = FlightsStatusEnum.valueOf(flights.getStatus());
                        if (FlightsStatusEnum.IS_READY.equals(flightsStatusEnum)) {
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
            }
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




    @Override
    public List<RequestTakingPlaneDto> getAdoptionFlights() {
        List<RequestTakingPlaneDto> list = new ArrayList<>();
        for (RequestsTakingPlaneEntity requestsTakingPlaneEntities : requestsTakingPlaneEntityRepository.findAll()){
            RequestTakingPlaneDto requestTakingPlaneDto = new RequestTakingPlaneDto();
            if (FlightsStatusEnum.ADOPTION.name().equals(requestsTakingPlaneEntities.getFlights().getStatus())){
                requestTakingPlaneDto.setModelPlane(requestsTakingPlaneEntities.getFlights().getAirPlansEntity().getModel());
                requestTakingPlaneDto.setIdFlights(requestsTakingPlaneEntities.getFlights().getAirPlansEntity().getId());
                requestTakingPlaneDto.setAirport(requestsTakingPlaneEntities.getAirportEntity().getTitle());
                requestTakingPlaneDto.setPlaceOfDeparture(requestsTakingPlaneEntities.getPlaceOfDeparture().getTitle());
                list.add(requestTakingPlaneDto);
            }
        }
        return list;
    }

    @Override
    public void confirmAdoption(Long id) throws FlyException {
        chiefDispatcherValidator.validateConfirmAdoption(id);
        Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
        Optional <FlightsEntity> flights = flightsEntityRepository.findByAirPlansEntity(plane.get());
        flights.get().setStatus("CONFIRM_ADOPTION");
        flightsEntityRepository.save(flights.get());
    }


//
//    @Override
//    public void addRequest(RequestDto requestDto) {
//        requestDtoList.add(requestDto);
//    }
//
//    @Override
//    public List<RequestDto> getRequestList() {
//        return requestDtoList;
//    }
//
//    @Override
//    public void addFlightsRequest(RequestFlightsDto requestFlightsDto) {
//        this.requestFlightsDto.add(requestFlightsDto);
//
//    }
//
//    @Override
//    public List<RequestFlightsDto> getRequestFlightsList() {
////        for (RequestFlightsDto dtp : requestFlightsDto){
////            //dtp.getFlightsEntity().setAirportEntity(null);
////        }
//        return requestFlightsDto;
//    }
//
//    @Override
//    public void registerPlane(String login) {
//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Порядок") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//                    AirPlansEntity airPlans = requestDto.getAirPlans();
//                    airPlans.setAirportEntity(user.get().getAirportEntity());
//
//                    airPlansEntityRepository.save(airPlans);
//                    requestDtoList.remove(requestDto);
//                    return;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void registerFlights(String login) {
//        for (RequestFlightsDto flightsDto : getRequestFlightsList()) {
//            if (flightsDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//                    List<AirPlansEntity> airPlansEntities = airPlansEntityRepository.findAll();
//
//                    FlightsEntity flights = flightsDto.getFlightsEntity();
//                    for (AirPlansEntity airPlans : airPlansEntities) {
//                        if (airPlans.getId().equals(flightsDto.getId())) {
//                            AirPlansEntity plans = airPlans;
//                            flights.setAirPlansEntity(plans);
//                            flights.setAirportEntity(user.get().getAirportEntity());
//
//
//                            flightsEntityRepository.save(flights);
//                            requestFlightsDto.remove(flightsDto);
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void addPlaneRequest(RequestPlaneDto requestPlaneDto) {
//    requestPlaneDtoList.add(requestPlaneDto);
//    }
//
//    @Override
//    public List<RequestPlaneDto> getRequestPlaneList() {
//        return requestPlaneDtoList;
//    }
}