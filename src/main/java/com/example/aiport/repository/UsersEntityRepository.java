package com.example.aiport.repository;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersEntityRepository extends JpaRepository<UsersEntity,Long> {
    Optional<UsersEntity> findByLogin(String login);

    List<UsersEntity> findAll();

    @Query(value = "select * from public.users us left join public.users_roles ur on us.id = ur.user_id left join roles r on ur.roles_id = r.id where ur.roles_id = 2",
            nativeQuery = true)
    List<UsersEntity> findManagersAirport();



    @Query(value = "select * from users us where flights_id = :id",nativeQuery = true)
    List<UsersEntity>   findUsersByFlights(@Param("id")Long id);

    @Query(value = "select * from public.users us left join public.users_roles ur on us.id = ur.user_id left join roles r on ur.roles_id = r.id where ur.roles_id = 10",
            nativeQuery = true)
    List<UsersEntity> findClient();

    @Query(value = "select * from public.users us left join public.users_roles ur on us.id = ur.user_id left join roles r on ur.roles_id = r.id where ur.roles_id = 9",
            nativeQuery = true)
    List<UsersEntity> findSteward();

    Optional<UsersEntity> findByAirPlansEntity(AirPlansEntity airPlansEntity);

    @Query(value = "select * from users u left join users_roles on  u.id = user_id where roles_id = :roles_id and airplane_id = :airplane_id", nativeQuery = true)
    List<UsersEntity> findByRolesIdAndAirplanes(@Param("roles_id")Long roles_id, @Param("airplane_id")Long airplane_id);


}

