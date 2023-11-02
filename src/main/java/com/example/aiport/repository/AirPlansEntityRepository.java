package com.example.aiport.repository;

import com.example.aiport.entity.AirPlansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirPlansEntityRepository extends JpaRepository<AirPlansEntity,Long> {

}
