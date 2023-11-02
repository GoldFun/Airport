package com.example.aiport.repository;

import com.example.aiport.entity.RequestsTakingPlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsTakingPlaneEntityRepository extends JpaRepository<RequestsTakingPlaneEntity,Long> {
}
