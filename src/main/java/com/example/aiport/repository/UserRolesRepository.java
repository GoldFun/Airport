package com.example.aiport.repository;

import com.example.aiport.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.aiport.entity.UserRoles;

import java.util.Optional;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {
    Optional<UserRoles> findById(Long id);
    Optional<UserRoles> findByUsersEntity(UsersEntity user);


}
