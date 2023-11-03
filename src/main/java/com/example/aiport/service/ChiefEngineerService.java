package com.example.aiport.service;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.exception.InvalidRequestException;

import java.util.List;

public interface ChiefEngineerService {
    public List<PlaneStatusDto> getPlaneList(String filter);
    public void giveOrder(Long id, String planeStatus) throws InvalidRequestException;




//    public void technicalInspection(String login) throws NoRequestUserException, UserNotFoundException;
//    public void addRequest(RequestDto requestDto);
//    public List<RequestDto> getRequestList();
//    public void repair(String login) throws NoRequestUserException, UserNotFoundException;
//    public RequestDto send(String login) throws NoRequestUserException, UserNotFoundException;
//
//    public void addPlaneRequest(RequestPlaneDto requestPlaneDto);
//    public List<RequestPlaneDto> getPlaneRequestList();
//    public void appointEngineer(String login) throws NoRequestUserException, UserNotFoundException;
//    public RequestPlaneDto transferDispatcher(String login) throws UserNotFoundException, NoRequestUserException;

}
