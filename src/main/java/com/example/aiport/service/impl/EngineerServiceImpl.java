package com.example.aiport.service.impl;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.EngineerService;
import com.example.aiport.validators.EngineerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EngineerServiceImpl implements EngineerService {


    private final AirPlansEntityRepository airPlansEntityRepository;
    private final EngineerValidator engineerValidator;
    private final UsersEntityRepository usersEntityRepository;


    @Autowired
    public EngineerServiceImpl(AirPlansEntityRepository airPlansEntityRepository,
                               EngineerValidator engineerValidator, UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.engineerValidator = engineerValidator;
        this.usersEntityRepository = usersEntityRepository;
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
                    if (PlaneStatus.CONFIRM.name().equals(planes.getStatus())) {
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
            for (AirPlansEntity planes : airPlansEntityRepository.findAll()) {
                PlaneStatusDto planeStatusDto = new PlaneStatusDto();

                if (PlaneStatus.TECH_REVIEW.name().equals(planes.getStatus()) ||
                        PlaneStatus.REPAIR.name().equals(planes.getStatus()) ||
                        PlaneStatus.REFUEL.name().equals((planes.getStatus()))) {
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
    public void techReview(Long id) throws FailTechReviewException {
        engineerValidator.validateTechReview(id);
        Optional<AirPlansEntity> plane =airPlansEntityRepository.findById(id);

        Random random = new Random();
        Boolean bool = random.nextBoolean();

        if (bool){
            plane.get().setStatus("NORMAL");
            airPlansEntityRepository.save(plane.get());
        }else {
            plane.get().setStatus("BROKEN");
            airPlansEntityRepository.save(plane.get());
        }


//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Технический осмотр") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//                    Random random = new Random();
//                    boolean randomValue = random.nextBoolean();
//
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("ENGINEER");
//                    requestDto.setAirPlans(requestDto.getAirPlans());
//                    requestDto.getAirPlans().setBroken(randomValue);
//                    if (!requestDto.getAirPlans().getBroken()) {
//                        requestDto.setRequest("Ремонт");
//                    } else {
//                        requestDto.setRequest("Порядок");
//                    }
//                    requestDtoList.remove(requestDto);
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


    }

    @Override
    public void repair(Long id) throws FailRepairException {
        engineerValidator.validateRepair(id);

        Optional<AirPlansEntity> plane =airPlansEntityRepository.findById(id);
        plane.get().setStatus("NORMAL");
        airPlansEntityRepository.save(plane.get());
    }

    @Override
    public List<PlaneStatusDto> getNewPlanes() {
        Long id = 0L;
        int j = 0;
        List<AirPlansEntity> list = airPlansEntityRepository.findAll();
        List<PlaneStatusDto> planeList = new ArrayList<>();
        for (AirPlansEntity plans : list){
            if (id < plans.getId()){
                id = plans.getId();
            }
        }
        for (int i=0; j<=2;i++){
            Optional<AirPlansEntity> plane = Optional.of(new AirPlansEntity());
            try {
                plane = airPlansEntityRepository.findById(id-i);
                if (PlaneStatus.CONFIRM.name().equals(plane.get().getStatus())){
                    PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                    planeStatusDto.setCountPlace(plane.get().getCountPlace());
                    planeStatusDto.setMarka(plane.get().getMarka());
                    planeStatusDto.setId(plane.get().getId());
                    planeStatusDto.setModel(plane.get().getModel());
                    planeStatusDto.setPlaneStatus(PlaneStatus.valueOf(plane.get().getStatus()));
                    planeList.add(planeStatusDto);
                    System.out.println(j);
                    j++;

                }
            }catch (NoSuchElementException e){
            }
        }
        return planeList;
    }

    @Override
    public void refuel(Long id) throws FailRefuelException {
    engineerValidator.validateRefuel(id);


    Optional <AirPlansEntity> plane = airPlansEntityRepository.findById(id);
    plane.get().setStatus("REFILLED");
    airPlansEntityRepository.save(plane.get());
    }



//    @Override
//    public RequestDto repair(String login) throws NoRequestUserException, UserNotFoundException {
//        for (RequestDto requestDto : getRequestList()) {
//            if (requestDto.getRequest().equals("Ремонт") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("ENGINEER");
//                    requestDto.setAirPlans(requestDto.getAirPlans());
//                    requestDto.getAirPlans().setBroken(true);
//                    requestDto.setRequest("Порядок");
//                    requestDtoList.remove(requestDto);
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
//        requestPlaneDtoList.add(requestPlaneDto);
//    }
//
//    @Override
//    public List<RequestPlaneDto> getRequestPlaneList() {
//        return requestPlaneDtoList;
//    }
//
//    @Override
//    public RequestPlaneDto refuelPlane(String login) {
//        for (RequestPlaneDto requestDto : getRequestPlaneList()) {
//            if (requestDto.getRequest().equals("Заправка самолета") && requestDto.getLogin().equals(login)) {
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication.getPrincipal() instanceof UserDetails) {
//                    String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//                    Optional<UsersEntity> user = usersEntityRepository.findByLogin(username);
//
//                    requestDto.setLogin(user.get().getLogin());
//                    requestDto.setRole("ENGINEER");
//                    requestDto.setRequest("Самолет заправлен");
//
//                    int id = Math.toIntExact(requestDto.getId());
//                    airPlansEntityRepository.findAll().get(id).setFueled(true);
//                    airPlansEntityRepository.save(airPlansEntityRepository.findAll().get(id));
//
//
//
//                    requestPlaneDtoList.remove(requestDto);
//                    return requestDto;
//                }
//            }
//        }
//        return null;
//    }


}