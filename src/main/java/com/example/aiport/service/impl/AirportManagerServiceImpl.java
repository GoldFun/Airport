package com.example.aiport.service.impl;

import com.example.aiport.dto.ChangeRoleDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.*;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.ActionOneselfException;
import com.example.aiport.exception.EmptyAnswerException;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.repository.*;
import com.example.aiport.service.AirportManagerService;
import com.example.aiport.validators.AirportManagerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirportManagerServiceImpl implements AirportManagerService {
    private final AirportManagerValidator airportManagerValidator;
    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolesEntityRepository rolesEntityRepository;
    private final RequestsJobsEntityRepository requestsJobsEntityRepository;
    private final FlightsEntityRepository flightsEntityRepository;
    private final HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository;
    private final ReadingsEntityRepository readingsEntityRepository;

    @Autowired
    public AirportManagerServiceImpl(AirportManagerValidator airportManagerValidator,
                                     UsersEntityRepository usersEntityRepository,
                                     UserRolesRepository userRolesRepository,
                                     RolesEntityRepository rolesEntityRepository,
                                     RequestsJobsEntityRepository requestsJobsEntityRepository,
                                     FlightsEntityRepository flightsEntityRepository,
                                     HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository,
                                     ReadingsEntityRepository readingsEntityRepository) {
        this.airportManagerValidator = airportManagerValidator;
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolesEntityRepository = rolesEntityRepository;
        this.requestsJobsEntityRepository = requestsJobsEntityRepository;
        this.flightsEntityRepository = flightsEntityRepository;
        this.historyPastFlightsEntityRepository = historyPastFlightsEntityRepository;
        this.readingsEntityRepository = readingsEntityRepository;
    }

    @Override
    public ChangeRoleDto changeRole(String login, String role) throws InvalidLoginException, InvalidRoleException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity>  authenticationUser = usersEntityRepository.findByLogin(username);

            airportManagerValidator.validateChangeRoleByRole(role);
            airportManagerValidator.validateRetireByLogin(login,authenticationUser.get());


            Optional<RolesEntity> titleRole = rolesEntityRepository.findByTitleIgnoreCase(role);
            Optional<UsersEntity> user = usersEntityRepository.findByLogin(login);
            Optional<UserRoles> userRole = userRolesRepository.findById(user.get().getId());

            user.get().setAirportEntity(authenticationUser.get().getAirportEntity());



            userRole.get().setRolesEntity(titleRole.get());
            usersEntityRepository.save(user.get());
            userRolesRepository.save(userRole.get());

        }
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        changeRoleDto.setRole(role);
        changeRoleDto.setLogin(login);
    return changeRoleDto;
    }

    @Override
    public void retire(String login) throws InvalidRoleException, InvalidLoginException, ActionOneselfException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            airportManagerValidator.validateRetireByLogin(login, authenticationUser.get());
        }
    Optional<UsersEntity> user = usersEntityRepository.findByLogin(login);
    Optional<UserRoles> userRole = userRolesRepository.findById(user.get().getId());
    Optional<RolesEntity> titleRole = rolesEntityRepository.findById(userRole.get().getRolesEntity().getId());
    airportManagerValidator.validateRetireByRole(userRole.get().getRolesEntity().getTitle());

    userRolesRepository.delete(userRole.get());
    usersEntityRepository.delete(user.get());

    }

    @Override
    public List<RequestsJobsEntity> getRequestJobsList() {
        return requestsJobsEntityRepository.findAll();
    }

    @Override
    public PromoltionEnum recruit(String login, String answer) throws EmptyAnswerException, InvalidLoginException {
    airportManagerValidator.validateRecruitByAnswer(answer);
    airportManagerValidator.validateRecruitByLogin(login);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);

            if (PromoltionEnum.ACCEPT.name().equalsIgnoreCase(answer)){
                Optional<RequestsJobsEntity> request = requestsJobsEntityRepository.findByLogin(login);
                requestsJobsEntityRepository.delete(request.get());

                Optional<UsersEntity> user = usersEntityRepository.findByLogin(login);
                Optional<UserRoles> userRoles = userRolesRepository.findById(user.get().getId());

                user.get().setAirportEntity(authenticationUser.get().getAirportEntity());

                userRoles.get().setRolesEntity(request.get().getRolesEntity());
                userRolesRepository.save(userRoles.get());
                return PromoltionEnum.ACCEPT;
            }else if (PromoltionEnum.REFUSE.name().equalsIgnoreCase(answer)){
                Optional<RequestsJobsEntity> request = requestsJobsEntityRepository.findByLogin(login);
                requestsJobsEntityRepository.delete(request.get());
                return PromoltionEnum.REFUSE;
            }
        }
    return null;
    }

    @Override
    public List<UserDto> getUserList() {
        List<UserDto> userDtoList =new ArrayList<>();
        for (UsersEntity users : usersEntityRepository.findAll()){
            UserDto userDto = new UserDto();
            userDto.setLogin(users.getLogin());
            userDto.setSurname(users.getSurname());
            Optional<UserRoles> idUserRole = userRolesRepository.findById(users.getId());
            userDto.setRole(idUserRole.get().getRolesEntity().getTitle());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public void reading() {
        ReadingsEntity readingsEntity = new ReadingsEntity();
        readingsEntity.setDate(LocalDate.now());
        System.out.println(1);
        List<FlightsEntity> flightsEntityList =flightsEntityRepository.findByDate(readingsEntity.getDate());
        System.out.println(11);
        int sizeFlightsEntityList = flightsEntityList.size();
        System.out.println(2);
        readingsEntity.setCountFlightUsed(sizeFlightsEntityList);
        System.out.println(22);
        List<HistoryPastFlightsEntity> historyPastFlightsEntityList = historyPastFlightsEntityRepository.findByDate(readingsEntity.getDate());
        System.out.println(3);
        int sizeHistoryPastFlightsEntityList = historyPastFlightsEntityList.size();
        System.out.println(33);
        readingsEntity.setCountClientsUsedServices(sizeHistoryPastFlightsEntityList);
        System.out.println(4);
        readingsEntityRepository.save(readingsEntity);
        System.out.println(5);

    }
}
