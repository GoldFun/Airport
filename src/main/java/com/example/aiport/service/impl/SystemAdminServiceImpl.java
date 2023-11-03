package com.example.aiport.service.impl;

import com.example.aiport.dto.ReadingDto;
import com.example.aiport.dto.UserDto;
import com.example.aiport.entity.ReadingsEntity;
import com.example.aiport.entity.RolesEntity;
import com.example.aiport.entity.UserRoles;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.exception.InvalidLoginException;
import com.example.aiport.exception.InvalidRoleException;
import com.example.aiport.repository.ReadingsEntityRepository;
import com.example.aiport.repository.RolesEntityRepository;
import com.example.aiport.repository.UserRolesRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.service.SystemAdminService;
import com.example.aiport.validators.SystemAdminValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SystemAdminServiceImpl implements SystemAdminService {
    private final SystemAdminValidator systemAdminValidator;
    private final UsersEntityRepository usersEntityRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolesEntityRepository rolesEntityRepository;
    private final ReadingsEntityRepository readingsEntityRepository;

    @Autowired
    public SystemAdminServiceImpl(SystemAdminValidator systemAdminValidator,
                                  UsersEntityRepository usersEntityRepository,
                                  UserRolesRepository userRolesRepository,
                                  RolesEntityRepository rolesEntityRepository,
                                  ReadingsEntityRepository readingsEntityRepository) {
        this.systemAdminValidator = systemAdminValidator;
        this.usersEntityRepository = usersEntityRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolesEntityRepository = rolesEntityRepository;
        this.readingsEntityRepository = readingsEntityRepository;
    }

    @Override
    public void changeRole(String role, String login) throws InvalidRoleException, InvalidLoginException {
        systemAdminValidator.validateChangeRoleByRole(role);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            systemAdminValidator.validateChangeRoleByLogin(login, authenticationUser.get());

            Optional<RolesEntity> titleRole = rolesEntityRepository.findByTitleIgnoreCase(role);
            Optional<UsersEntity> user = usersEntityRepository.findByLogin(login);
            Optional<UserRoles> userRole = userRolesRepository.findById(user.get().getId());

            user.get().setAirportEntity(authenticationUser.get().getAirportEntity());

            userRole.get().setRolesEntity(titleRole.get());
            usersEntityRepository.save(user.get());
            userRolesRepository.save(userRole.get());
        }
    }

    @Override
    public List<UserDto> getUserList() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (UsersEntity users : usersEntityRepository.findAll()) {
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
    public List<ReadingDto> getReadings() {
        System.out.println(2);
        List<ReadingDto> readingDtoList = new ArrayList<>();
        for (ReadingsEntity readingsEntity : readingsEntityRepository.findAll()) {
            ReadingDto readingDto = new ReadingDto();
            readingDto.setDate(String.valueOf(readingsEntity.getDate()));
            readingDto.setCountClientsUsedServices(readingsEntity.getCountClientsUsedServices());
            readingDto.setCountFlightUsed(readingsEntity.getCountFlightUsed());
            readingDtoList.add(readingDto);
        }
        return readingDtoList;
    }
}
