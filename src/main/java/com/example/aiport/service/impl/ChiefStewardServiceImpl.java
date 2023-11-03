package com.example.aiport.service.impl;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.FlightsEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.FlightsEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.ChiefStewardService;
import com.example.aiport.validators.ChiefStewardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChiefStewardServiceImpl implements ChiefStewardService {
    private final ChiefStewardValidator chiefStewardValidator;
    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    @Autowired
    public ChiefStewardServiceImpl(ChiefStewardValidator chiefStewardValidator,
                                   UsersEntityRepository usersEntityRepository,
                                   UserRolesRepository userRolesRepository,
                                   AirPlansEntityRepository airPlansEntityRepository,
                                   FlightsEntityRepository flightsEntityRepository) {
        this.chiefStewardValidator = chiefStewardValidator;
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
    }

    @Override
    public void scheduleBriefing() throws StewardException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            chiefStewardValidator.validateScheduleBriefing(authenticationUser.get());


                for (UsersEntity user : usersEntityRepository.findSteward()){

                    if (user.getAirPlansEntity().getId().equals(authenticationUser.get().getAirPlansEntity().getId())){

                        Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(user);
                        userRoles.get().setStatus("BRIEFING");
                        System.out.println(123);
                        userRolesRepository.save(userRoles.get());
                    }
                }



    }

    @Override
    public void scheduleGiveFood() throws StewardException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {

            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            chiefStewardValidator.validateScheduleGiveFood(authenticationUser.get());

            for (UsersEntity user : usersEntityRepository.findSteward()){

                if (user.getAirPlansEntity().getId().equals(authenticationUser.get().getAirPlansEntity().getId())){

                    Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(user);
                    userRoles.get().setStatus("GIVE_FOOD");
                    System.out.println(123);
                    userRolesRepository.save(userRoles.get());
                }
            }
        }
    }
    @Override
    public List<PlaneStatusDto> getPlanes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            List<PlaneStatusDto> list = new ArrayList<>();
            for (AirPlansEntity plans : airPlansEntityRepository.findAll()) {
                if (plans.getStatus().equals("CONFIRM")) {
                    Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(authenticationUser.get());
                    List<UsersEntity> user = usersEntityRepository.findByRolesIdAndAirplanes(userRoles.get().getRolesEntity().getId(),plans.getId());
                    if (user.isEmpty()) {
                        PlaneStatusDto planeStatusDto = new PlaneStatusDto();
                        planeStatusDto.setPlaneStatus(PlaneStatus.valueOf(plans.getStatus()));
                        planeStatusDto.setModel(plans.getModel());
                        planeStatusDto.setMarka(plans.getMarka());
                        planeStatusDto.setId(plans.getId());
                        planeStatusDto.setCountPlace(plans.getCountPlace());
                        list.add(planeStatusDto);
                    }
                }
            }
            return list;
        }
        return null;
    }
    @Override
    public void wantPlane(Long id) throws InvalidPlaneException {
        AirPlansEntity plane = chiefStewardValidator.validateWantPlaneNull(id);
        chiefStewardValidator.validateWantPlane(id, plane);
        Optional<AirPlansEntity> airPlansEntity = airPlansEntityRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            authenticationUser.get().setAirPlansEntity(airPlansEntity.get());
            usersEntityRepository.save(authenticationUser.get());
        }
    }
}
