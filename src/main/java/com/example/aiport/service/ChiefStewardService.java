package com.example.aiport.service;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;

import java.util.List;

public interface ChiefStewardService {
    public void scheduleBriefing() throws StewardException;
    public void scheduleGiveFood() throws StewardException;
    public List<PlaneStatusDto> getPlanes();
    public void wantPlane(Long id) throws InvalidPlaneException;
}
