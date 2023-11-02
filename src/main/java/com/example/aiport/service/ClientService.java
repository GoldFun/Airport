package com.example.aiport.service;

import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.HistoryPastFlightsDto;
import com.example.aiport.dto.ReviewsDto;
import com.example.aiport.exception.*;

import java.util.List;

public interface ClientService {
//    public void registerFlights(int id) throws InvalidFlightsException;
//    public List<String> getFlights();
    public void applyJob(String role, String message) throws InvalidRoleException, EmptyMessageException, InvalidLoginException; //отправит запрос на на работу
    public List<FlightsDto> getFlights(); //вернет доступные рейсы
    public void registerFlights(Long id) throws InvalidFlightsException; //регистрируется на рейс
    public void logOutFlights() throws LogOutException;
    public FlightsDto getFlight() throws InvalidFlightsException;
    public void review(String message, Long id) throws ReviewsException;
    public List<HistoryPastFlightsDto> getHistory();
}
