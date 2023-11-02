package com.example.aiport.repository;

import com.example.aiport.entity.RequestsJobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestsJobsEntityRepository extends JpaRepository<RequestsJobsEntity,Long> {
    public Optional<RequestsJobsEntity> findByLogin(String login);
}
