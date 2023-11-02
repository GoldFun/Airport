package com.example.aiport.repository;

import com.example.aiport.entity.*;
import org.h2.engine.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestPropertySource(locations = "classpath:applicationsTest.properties")
class UsersEntityRepositoryTest {
    @Autowired
    public UsersEntityRepository usersEntityRepository;
    @Autowired
    public RolesEntityRepository rolesEntityRepository;
    @Autowired
    public UserRolesRepository userRolesRepository;


    @Test
    public void test_findManagersAirport_OK() {
        UsersEntity user = new UsersEntity();
        user.setLogin("TestDB");
        user.setAirportEntity(new AirportEntity());
        user.setSurname("TestDB");
        user.setPassword("TestDB");
        RolesEntity roles = new RolesEntity();
        roles.setTitle("MANAGER_AIRPORT");
        rolesEntityRepository.save(roles);
        UserRoles userRoles = new UserRoles();
        userRoles.setUsersEntity(user);
        userRoles.setRolesEntity(roles);
        userRolesRepository.save(userRoles);
        Optional<UsersEntity> savedUser = usersEntityRepository.findByLogin("TestDB");
        Optional<UserRoles> savedUserRoles = userRolesRepository.findByUsersEntity(savedUser.get());
        Assertions.assertEquals(user.getLogin(),savedUser.get().getLogin());
        Assertions.assertEquals(userRoles.getRolesEntity(),savedUserRoles.get().getRolesEntity());


    }
    @Test
    public void test_findUsersByFlights(){
        FlightsEntity flights = new FlightsEntity();
        AirPlansEntity airPlane = new AirPlansEntity();
        flights.setTitle("TestDB");
        flights.setAirportEntity(new AirportEntity());
        flights.setPlaceOfDeparture(new AirportEntity());
        flights.setAirPlansEntity(airPlane);
        UsersEntity user = new UsersEntity();
        user.setLogin("TestBD1");
        user.setPassword("TestBD1");
        user.setSurname("TestBD1");
        user.setFlightsEntity(flights);
        UsersEntity user2 = new UsersEntity();
        user2.setLogin("TestDB2");
        user2.setPassword("TestDB2");
        user2.setSurname("TestDB2");
        user2.setFlightsEntity(flights);
        List<UsersEntity> users = usersEntityRepository.findUsersByFlights(flights.getId());
        List<UsersEntity> users2 = new ArrayList<>();
        users2.add(user);
        users2.add(user2);
        int i =0;
        for (UsersEntity flightsUsers : users){
            Assertions.assertEquals(users2.get(i).getLogin(),flightsUsers.getLogin());
            i++;
        }

    }

    @Test
    public void test_findClient_OK(){
        UsersEntity user = new UsersEntity();
        user.setLogin("TestDB");
        user.setAirportEntity(new AirportEntity());
        user.setSurname("TestDB");
        user.setPassword("TestDB");
        RolesEntity roles = new RolesEntity();
        roles.setTitle("CLIENT");
        rolesEntityRepository.save(roles);
        UserRoles userRoles = new UserRoles();
        userRoles.setUsersEntity(user);
        userRoles.setRolesEntity(roles);
        userRolesRepository.save(userRoles);
        Optional<UsersEntity> savedUser = usersEntityRepository.findByLogin("TestDB");
        Optional<UserRoles> savedUserRoles = userRolesRepository.findByUsersEntity(savedUser.get());
        Assertions.assertEquals(user.getLogin(),savedUser.get().getLogin());
        Assertions.assertEquals(userRoles.getRolesEntity(),savedUserRoles.get().getRolesEntity());
    }
    @Test
    public void test_findSteward_OK(){
        UsersEntity user = new UsersEntity();
        user.setLogin("TestDB");
        user.setAirportEntity(new AirportEntity());
        user.setSurname("TestDB");
        user.setPassword("TestDB");
        RolesEntity roles = new RolesEntity();
        roles.setTitle("STEWARD");
        rolesEntityRepository.save(roles);
        UserRoles userRoles = new UserRoles();
        userRoles.setUsersEntity(user);
        userRoles.setRolesEntity(roles);
        userRolesRepository.save(userRoles);
        Optional<UsersEntity> savedUser = usersEntityRepository.findByLogin("TestDB");
        Optional<UserRoles> savedUserRoles = userRolesRepository.findByUsersEntity(savedUser.get());
        Assertions.assertEquals(user.getLogin(),savedUser.get().getLogin());
        Assertions.assertEquals(userRoles.getRolesEntity(),savedUserRoles.get().getRolesEntity());
    }
    @Test
    public void test_findByRolesIdAndAirplanes_OK(){
        AirPlansEntity airPlansEntity = new AirPlansEntity();
        RolesEntity role = new RolesEntity();
        role.setTitle("PILOT");
        airPlansEntity.setMarka("TestDB");
        airPlansEntity.setModel("TestDB");
        airPlansEntity.setCountPlace(5);
        UsersEntity user = new UsersEntity();
        user.setLogin("TestBD1");
        user.setPassword("TestBD1");
        user.setSurname("TestBD1");
        user.setAirPlansEntity(airPlansEntity);
        UserRoles userRole = new UserRoles();
        userRole.setRolesEntity(role);
        userRole.setUsersEntity(user);
        UsersEntity user2 = new UsersEntity();
        user2.setLogin("TestDB2");
        user2.setPassword("TestDB2");
        user2.setSurname("TestDB2");
        user2.setAirPlansEntity(airPlansEntity);
        UserRoles userRole1 = new UserRoles();
        userRole1.setRolesEntity(role);
        userRole1.setUsersEntity(user2);
        List<UsersEntity> users = usersEntityRepository.findByRolesIdAndAirplanes(role.getId(),airPlansEntity.getId());
        List<UsersEntity> users2 = new ArrayList<>();
        users2.add(user);
        users2.add(user2);
        int i =0;
        for (UsersEntity searchUsers : users){
            Assertions.assertEquals(users2.get(i).getLogin(),searchUsers.getLogin());
            i++;
        }
    }



}