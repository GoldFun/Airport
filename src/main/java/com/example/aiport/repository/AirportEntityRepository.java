package com.example.aiport.repository;

import com.example.aiport.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportEntityRepository extends JpaRepository<AirportEntity, Long> {
}
