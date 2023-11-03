package com.example.aiport.service;
import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.FailRefuelException;
import com.example.aiport.exception.FailRepairException;
import com.example.aiport.exception.FailTechReviewException;
import com.example.aiport.exception.UserNotFoundException;

import java.util.List;

public interface EngineerService {
    public List<PlaneStatusDto> getPlanes(String filter); //вернет самолеты с tech review и repair
    public void techReview(Long id) throws FailTechReviewException; //тех осмотр
    public void repair(Long id) throws FailRepairException; //починить самолет поменяв статус на normal
    public List<PlaneStatusDto> getNewPlanes(); //посмотрит последние самолеты которые вышли
    public void refuel(Long id) throws FailRefuelException;
//    public void addRequest(RequestDto requestDto);
//    public List<RequestDto> getRequestList();

//    public void addPlaneRequest(RequestPlaneDto requestPlaneDto);
//    public List<RequestPlaneDto> getRequestPlaneList();
//    public RequestPlaneDto refuelPlane(String login);
}
