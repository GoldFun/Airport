package com.example.aiport.repository;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.FlightsEntity;
import com.example.aiport.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightsEntityRepository extends JpaRepository<FlightsEntity,Long> {
    Optional<FlightsEntity> findByAirPlansEntity(AirPlansEntity airPlansEntity);

    List<FlightsEntity> findByDate(LocalDate date);


}
