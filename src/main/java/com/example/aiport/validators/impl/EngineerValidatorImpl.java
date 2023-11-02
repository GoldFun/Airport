package com.example.aiport.validators.impl;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.entity.UsersEntity;
import com.example.aiport.entity.atribute.PlaneStatus;
import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;
import com.example.aiport.repository.AirPlansEntityRepository;
import com.example.aiport.repository.UsersEntityRepository;
import com.example.aiport.validators.EngineerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class EngineerValidatorImpl implements EngineerValidator {
    private final AirPlansEntityRepository airPlansEntityRepository;
    private final UsersEntityRepository usersEntityRepository;

    @Autowired
    public EngineerValidatorImpl(AirPlansEntityRepository airPlansEntityRepository,
                                 UsersEntityRepository usersEntityRepository) {
        this.airPlansEntityRepository = airPlansEntityRepository;
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public void validateTechReview(Long id) throws FailTechReviewException {
        if (Objects.isNull(id)) {
            throw new FailTechReviewException("Пустой id!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);

            if (airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                for (AirPlansEntity plans : airPlansEntityRepository.findAll()) {
                    if (plans.getId().equals(id)) {
                        if (PlaneStatus.TECH_REVIEW.name().equals(plans.getStatus())) {
                            return;
                        }
                        throw new FailTechReviewException("Самолет не соответствует статусу TECH_REVIEW!");
                    }
                }
            }
            throw new FailTechReviewException("Этот самолет принадлежит другому арэрпорту!");
        }


    }

    @Override
    public void validateRepair(Long id) throws FailRepairException {
        if (Objects.isNull(id)) {
            throw new FailRepairException("Пустой id!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);

            if (airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                if (PlaneStatus.REPAIR.name().equals(plane.get().getStatus())) {
                    return;
                }
                throw new FailRepairException("Самолет не соответствует статусу REPAIR!");
            }
            throw new FailRepairException("Этот самолет принадлежит другому арэрпорту!");
        }

    }

    @Override
    public void validateRefuel(Long id) throws FailRefuelException {
        if (Objects.isNull(id)) {
            throw new FailRefuelException("Пустой id!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<UsersEntity> authenticationUser = usersEntityRepository.findByLogin(username);
            Optional<AirPlansEntity> airPlans = airPlansEntityRepository.findById(id);

            if (airPlans.get().getAirportEntity().equals(authenticationUser.get().getAirportEntity())) {
                Optional<AirPlansEntity> plane = airPlansEntityRepository.findById(id);
                if (PlaneStatus.REFUEL.name().equals(plane.get().getStatus())) {
                    return;
                }
                throw new FailRefuelException("Самолет не соответствует статусу REFUEL!");
            }
            throw new FailRefuelException("Этот самолет принадлежит другому арэрпорту!");
        }
    }
}

