package com.example.aiport.service.impl;


import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.InvalidRequestException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.ChiefEngineerService;
import com.example.aiport.validators.ChiefEngineerValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChiefEngineerServiceImpl implements ChiefEngineerService {
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final ChiefEngineerValidator chiefEngineerValidator;
    private final UsersEntityRepository usersEntityRepository;

    public ChiefEngineerServiceImpl(AirPlansEntityRepository airPlansEntityRepository,
                                    ChiefEngineerValidator chiefEngineerValidator,
                                    UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.chiefEngineerValidator = chiefEngineerValidator;
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public List<PlaneStatusDto> getPlaneList(String filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);


            List<PlaneStatusDto> planeList = new ArrayList<>();
            if (!Objects.isNull(filter)) {
                if (filter.equalsIgnoreCase("tech_review")) {
                    for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        if (PlaneStatus.NORMAL.name().equals(planes.getStatus()) || PlaneStatus.BROKEN.name().equals(planes.getStatus())) {
                            if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                                PlaneStatus status = PlaneStatus.valueOf(planes.getStatus());
                                planeStatusDto.setMarka(planes.getMarka());
                                planeStatusDto.setCountPlace(planes.getCountPlace());
                                planeStatusDto.setModel(planes.getModel());
                                planeStatusDto.setId(planes.getId());
                                planeStatusDto.setPlaneStatus(status);
                                planeList.add(planeStatusDto);
                            }

                        }
                    }
                    return planeList;
                }
                if (filter.equalsIgnoreCase("confirm")) {
                    for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        if (PlaneStatus.CONFIRM.name().equals(planes.getStatus())) {
                            if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                                PlaneStatus status = PlaneStatus.valueOf(planes.getStatus());
                                planeStatusDto.setMarka(planes.getMarka());
                                planeStatusDto.setCountPlace(planes.getCountPlace());
                                planeStatusDto.setModel(planes.getModel());
                                planeStatusDto.setId(planes.getId());
                                planeStatusDto.setPlaneStatus(status);
                                planeList.add(planeStatusDto);
                            }

                        }
                    }
                    return planeList;
                }
                if (filter.equalsIgnoreCase("is_busy")) {
                    for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        if (PlaneStatus.IS_BUSY.name().equals(planes.getStatus())) {
                            if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                                PlaneStatus status = PlaneStatus.valueOf(planes.getStatus());
                                planeStatusDto.setMarka(planes.getMarka());
                                planeStatusDto.setCountPlace(planes.getCountPlace());
                                planeStatusDto.setModel(planes.getModel());
                                planeStatusDto.setId(planes.getId());
                                planeStatusDto.setPlaneStatus(status);
                                planeList.add(planeStatusDto);
                            }
                        }
                    }
                    return planeList;
                }


            }
            for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                if (Objects.isNull(planes.getStatus())) {
                    if (planes.getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
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
    public void giveOrder(Long id, String planeStatus) throws InvalidRequestException {
    chiefEngineerValidator.validateGiveOrder( id,planeStatus);

    Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
    if(PlaneStatus.REPAIR.name().equalsIgnoreCase(planeStatus)){
        planeStatus = "REPAIR";
        plane.get().setStatus(planeStatus);
        airPlansEntityRepository.save(plane.get());
    }
    if (PlaneStatus.TECH_REVIEW.name().equalsIgnoreCase(planeStatus)){
        planeStatus = "TECH_REVIEW";
        plane.get().setStatus(planeStatus);
        airPlansEntityRepository.save(plane.get());
    }
    if(PlaneStatus.REFUEL.name().equalsIgnoreCase(planeStatus)){
        planeStatus = "REFUEL";
        plane.get().setStatus((planeStatus));
        airPlansEntityRepository.save(plane.get());
    }

    }






//    private final UsersEntityRepository usersEntityRepository;
//    private final EngineerService engineerService;
//    private List<RequestDto> requestDtoList = new ArrayList<>();
//    private List<RequestPlaneDto> requestPlaneDto = new ArrayList<>();
//
//
//    @Autowired
//    public ChiefEngineerServiceImpl(UsersEntityRepository usersEntityRepository, EngineerService engineerService) {
//        this.usersEntityRepository = usersEntityRepository;
//        this.engineerService = engineerService;
//    }
//
//    @Override
//    public void technicalInspection(String login) throws NoRequestUserException, UserNotFoundException {
//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Технический осмотр") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("CHIEF_ENGINEER");
//                    requestDto.setRequest("Технический осмотр");
//                    requestDto.setAirPlans(requestDto.getAirPlans());
//                    engineerService.addRequest(requestDto);
//
//                    requestDtoList.remove(requestDto);
//                    return;
//                }
//            }
//
//        }
//
//        List<UsersEntity> usersEntityList = usersEntityRepository.findAll();
//        for (UsersEntity users : usersEntityList) {
//            if (users.getLogin().equals(login)) {
//                throw new NoRequestUserException("В данный момент от пользователя " + login + " нет запросов");
//            }
//        }
//        throw new UserNotFoundException("Такого пользователя нету в системе");
//    }
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
//    public void repair(String login) throws NoRequestUserException, UserNotFoundException {
//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Ремонт") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("CHIEF_ENGINEER");
//                    requestDto.setRequest("Ремонт");
//                    requestDto.setAirPlans(requestDto.getAirPlans());
//                    requestDto.getAirPlans().setBroken(requestDto.getAirPlans().getBroken());
//                    engineerService.addRequest(requestDto);
//
//                    requestDtoList.remove(requestDto);
//                    return;
//                }
//            }
//        }
//        List<UsersEntity> usersEntityList = usersEntityRepository.findAll();
//        for (UsersEntity users : usersEntityList) {
//            if (users.getLogin().equals(login)) {
//                throw new NoRequestUserException("В данный момент от пользователя " + login + " нет запросов");
//            }
//        }
//        throw new UserNotFoundException("Такого пользователя нету в системе");
//    }
//
//    @Override
//    public RequestDto send(String login) throws NoRequestUserException, UserNotFoundException {
//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Порядок") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRequest("Порядок");
//                    requestDto.setRole("CHIEF_ENGINEER");
//                    requestDto.setAirPlans(requestDto.getAirPlans());
//
//                    requestDtoList.remove(requestDto);
//
//                    return requestDto;
//                }
//            }
//        }
//        List<UsersEntity> usersEntityList = usersEntityRepository.findAll();
//        for (UsersEntity users : usersEntityList) {
//            if (users.getLogin().equals(login)) {
//                throw new NoRequestUserException("В данный момент от пользователя " + login + " нет запросов");
//            }
//        }
//        throw new UserNotFoundException("Такого пользователя нету в системе");
//    }
//
//    @Override
//    public void addPlaneRequest(RequestPlaneDto requestPlaneDto) {
//
//        this.requestPlaneDto.add(requestPlaneDto);
//    }
//
//    @Override
//    public List<RequestPlaneDto> getPlaneRequestList() {
//        return requestPlaneDto;
//    }
//
//    @Override
//    public void appointEngineer(String login) throws NoRequestUserException, UserNotFoundException {
//        for (RequestPlaneDto requestDto : getPlaneRequestList()) {
//            if (requestDto.getRequest().equals("Заправка самолета") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("CHIEF_ENGINEER");
//                    requestDto.setRequest("Заправка самолета");
//                    this.requestPlaneDto.remove(requestDto);
//                    engineerService.addPlaneRequest(requestDto);
//
//
//                    return;
//                }
//            }
//        }
//        List<UsersEntity> usersEntityList = usersEntityRepository.findAll();
//        for (UsersEntity users : usersEntityList) {
//            if (users.getLogin().equals(login)) {
//                throw new NoRequestUserException("В данный момент от пользователя " + login + " нет запросов");
//            }
//        }
//        throw new UserNotFoundException("Такого пользователя нету в системе");
//    }
//
//    @Override
//    public RequestPlaneDto transferDispatcher(String login) throws UserNotFoundException, NoRequestUserException {
//        for (RequestPlaneDto requestDto : getPlaneRequestList()) {
//            if (requestDto.getRequest().equals("Самолет заправлен") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("CHIEF_ENGINEER");
//                    requestDto.setRequest("Самолет заправлен");
//
//                    requestPlaneDto.remove(requestDto);
//                    return requestDto;
//
//                }
//            }
//        }
//        List<UsersEntity> usersEntityList = usersEntityRepository.findAll();
//        for (UsersEntity users : usersEntityList) {
//            if (users.getLogin().equals(login)) {
//                throw new NoRequestUserException("В данный момент от пользователя " + login + " нет запросов");
//            }
//        }
//        throw new UserNotFoundException("Такого пользователя нету в системе");
//    }
}
