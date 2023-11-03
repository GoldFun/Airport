//package com.example.aiport.service.impl;
//
//import com.example.aiport.dto.ChangeRoleDto;
//import com.example.aiport.exception.InvalidLoginException;
//import com.example.aiport.exception.InvalidRoleException;
//import com.example.aiport.repository.*;
//import com.example.aiport.service.AirportManagerService;
//import com.example.aiport.validators.AirportManagerValidator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
//public class AirportManagerServiceImplTest {
//    @Spy
//    private  AirportManagerValidator airportManagerValidator;
//    @Spy
//    private  UsersEntityRepository usersEntityRepository;
//    @Spy
//    private  UserRolesRepository userRolesRepository;
//    @Spy
//    private  RolesEntityRepository rolesEntityRepository;
//    @Spy
//    private  RequestsJobsEntityRepository requestsJobsEntityRepository;
//    @Spy
//    private  FlightsEntityRepository flightsEntityRepository;
//    @Spy
//    private  HistoryPastFlightsEntityRepository historyPastFlightsEntityRepository;
//    @Spy
//    private  ReadingsEntityRepository readingsEntityRepository;
//    private UserDetails userDetails;
//    private Authentication authentication;
//
//@Test
//public void test_changeRole_OK() throws InvalidLoginException, InvalidRoleException {
//    AirportManagerService airportManagerService = new AirportManagerServiceImpl(this.airportManagerValidator,
//            this.usersEntityRepository,
//            this.userRolesRepository,
//            this.rolesEntityRepository,
//            this.requestsJobsEntityRepository,
//            this.flightsEntityRepository,
//            this.historyPastFlightsEntityRepository,
//            this.readingsEntityRepository);
//
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
////    Assertions.assertEquals(changeRoleDto.getLogin(),"test");
////    Assertions.assertEquals(changeRoleDto.getRole(),"ENGINEER");
//}
//}