package com.example.aiport.service;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.dto.ReviewsDto;
import com.example.aiport.dto.ReviewsDtoWithUsers;
import com.example.aiport.entity.AirPlansEntity;
import com.example.aiport.exception.FlyException;
import com.example.aiport.exception.InvalidPlaneException;

import java.util.List;

public interface PilotService {
public String getAnswer();
public  void takeOff() throws FlyException, InterruptedException;
public void planeLanding() throws FlyException;
public void landing() throws FlyException;
public List<ReviewsDtoWithUsers> getReviews();
public void wantPlane(Long id) throws InvalidPlaneException;
public List<PlaneStatusDto> getPlanes();

}
