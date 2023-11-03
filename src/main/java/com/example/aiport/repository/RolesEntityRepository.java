package com.example.aiport.repository;

import com.example.aiport.entity.RolesEntity;
import com.example.aiport.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesEntityRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByTitleIgnoreCase(String title);

}
