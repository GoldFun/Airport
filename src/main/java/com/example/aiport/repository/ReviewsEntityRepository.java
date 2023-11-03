package com.example.aiport.repository;

import com.example.aiport.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsEntityRepository extends JpaRepository<ReviewsEntity,Long> {
}
