package com.example.aiport.repository;

import com.example.aiport.entity.ReadingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingsEntityRepository extends JpaRepository<ReadingsEntity,Long> {
}
