package com.example.aiport.repository;

import com.example.aiport.entity.HistoryPastFlightsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryPastFlightsEntityRepository extends JpaRepository<HistoryPastFlightsEntity, Long> {
    List<HistoryPastFlightsEntity> findByDate(LocalDate date);
}
