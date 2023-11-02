package com.example.aiport.validators;

import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.exception.FailSendException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidFlightsException;
import com.example.aiport.exception.InvalidPlaneException;

public interface DispatcherValidator {
    public void validateCreatePlane(String model, String marka, Integer countPlace) throws InvalidPlaneException;
    public void validateSendChiefDispatcher(Long id) throws FailSendException;
    public void validateCreateFlights(String title, Long placeOfDeparture, Long id) throws InvalidFlightsException;
    public AirPlansEntity validateSendChiefDispatcherFlights(Long id) throws FailSendException;
    public void validateAcceptTakingPlane(Long id) throws FlyException;
}
