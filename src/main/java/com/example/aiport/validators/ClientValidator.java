package com.example.aiport.validators;

import com.example.aiport.entity.FlightsEntity;
import com.example.aiport.exception.*;

import java.util.Optional;

public interface ClientValidator {
    public void validateApplyJobByRole(String role) throws InvalidRoleException;
    public void validateApplyJobByMessage(String message) throws EmptyMessageException;
    public void validateApplyJobsByLogin(String login) throws InvalidLoginException;
    public void validateRegisterFlights(Long id) throws InvalidFlightsException;
    public void validateLogoutFlights(Long id) throws LogOutException;
    public Optional<FlightsEntity> validateReviews(Long id, String message) throws ReviewsException;
}
