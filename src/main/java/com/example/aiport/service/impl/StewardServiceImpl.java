package com.example.aiport.service.impl;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.StewardService;
import com.example.aiport.validators.StewardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StewardServiceImpl implements StewardService {
    private final StewardValidator stewardValidator;
    private final UserRolesRepository userRolesRepository;
    private final UsersEntityRepository usersEntityRepository;
    private final AirPlansEntityRepository airPlansEntityRepository;
    @Autowired
    public StewardServiceImpl(StewardValidator stewardValidator,
                              UserRolesRepository userRolesRepository,
                              UsersEntityRepository usersEntityRepository,
                              AirPlansEntityRepository airPlansEntityRepository) {
        this.stewardValidator = stewardValidator;
        this.userRolesRepository = userRolesRepository;
        this.usersEntityRepository = usersEntityRepository;

        this.airPlansEntityRepository = airPlansEntityRepository;
    }

    @Override
    public void Briefing() throws StewardException {
    UsersEntity user = stewardValidator.validateBriefing();
        System.out.println("инструктаж!");
        Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(user);
        userRoles.get().setStatus(null);
        userRolesRepository.save(userRoles.get());
    }

    @Override
    public void giveFood() throws StewardException {
        UsersEntity user = stewardValidator.validateGiveFood();
        System.out.println("раздача!");
        Optional<UserRoles> userRoles = userRolesRepository.findByUsersEntity(user);
        userRoles.get().setStatus(null);
        userRolesRepository.save(userRoles.get());
    }
    @Override
    public void wantPlane(Long id) throws InvalidPlaneException {
        AirPlansEntity plane = stewardValidator.validateWantPlaneNull(id);
        stewardValidator.validateWantPlane(id, plane);
        Optional<AirPlansEntity> airPlansEntity = airPlansEntityRepository.findById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            authenticationUser.get().setAirPlansEntity(airPlansEntity.get());
            usersEntityRepository.save(authenticationUser.get());
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
                    if (user.isEmpty() || user.size() == 2 || user.size() == 1) {
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

}
