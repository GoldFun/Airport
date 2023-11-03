package com.example.aiport.service;



import com.example.aiport.dto.FlightsDto;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.RequestTakingPlaneDto;
import com.example.aiport.entity.atribute.FlightsStatusEnum;
import com.example.aiport.entity.atribute.PromoltionEnum;
import com.example.aiport.exception.FailConfirmException;
import com.example.aiport.exception.FlyException;

import java.util.List;

public interface ChiefDispatcherService {
      public List<PlaneStatusDto> getPlanes(String filter); //выведет все самолеты с статусом SEND
      public PromoltionEnum confirm(Long id, String answer) throws FailConfirmException; //регистрирует самолет
      public PromoltionEnum confirmFlights(Long id, String answer) throws FailConfirmException; //регирирует рейс
      public List<FlightsDto> getConfirmFlights(String filter); //выведет все рейсы с статусом confirm
      public List<RequestTakingPlaneDto> getAdoptionFlights();
      public void confirmAdoption(Long id) throws FlyException;

      public PromoltionEnum confirmSendingFlights(Long id, String answer) throws FailConfirmException;

//    public void addFlightsRequest(RequestFlightsDto requestFlightsDto);
//    public List<RequestFlightsDto> getRequestFlightsList();
//    public void registerPlane(String login);
//    public void registerFlights(String login);
//    public void addPlaneRequest(RequestPlaneDto requestPlaneDto);
//    public List<RequestPlaneDto> getRequestPlaneList();
}
