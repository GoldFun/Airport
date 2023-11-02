package com.example.aiport.service;
import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.entity.RequestsTakingPlaneEntity;
import com.example.aiport.exception.FailSendException;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidFlightsException;
import com.example.aiport.exception.InvalidPlaneException;

import java.util.List;
import java.util.Optional;

public interface DispatcherService {
    public void createPlane(String model,String marka,Integer countPlace) throws InvalidPlaneException; //создает самолет
    public List<PlaneStatusDto> getPlanes(String filter); //выводит самолеты со статусом NORMAL
    public void sendChiefDispatcher(Long id) throws FailSendException; // меняет статус самолета на SEND
    public void sendChiefDispatcherFlights(Long id) throws FailSendException;
    public void createFlights(String title, Long placeOfDeparture, Long id ) throws InvalidFlightsException; //создает рейс
    public List<FlightsDto> getConfirmFlights(); //выведет все рейсы с статусом confirm

    public List<RequestTakingPlaneDto> getTakingPlane();
    public void acceptTakingPlane(Long id) throws FlyException;


//    public void addRequest(RequestDto requestDto);
//    public List<RequestDto> getRequestList();
//    public void send(String login) throws NoRequestUserException, UserNotFoundException;
//    public void createFlights(String title,String placeOfDeparture,Long id) throws InvalidFlightsException;
//    public void addPlaneRequest(RequestPlaneDto requestDto);
//    public List<RequestPlaneDto> getPlaneRequestList();
//    public void appointEngineer(String login) throws UserNotFoundException, NoRequestUserException;
//    public RequestPlaneDto transferChiefDispatcher(String login) throws NoRequestUserException, UserNotFoundException;

}
