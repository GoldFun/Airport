package com.example.aiport.service;

import com.example.aiport.dto.PlaneStatusDto;
import com.example.aiport.exception.InvalidPlaneException;
import com.example.aiport.exception.StewardException;

import java.util.List;

public interface StewardService {
    public void Briefing() throws StewardException;
    public void giveFood() throws StewardException;
    public void wantPlane(Long id) throws InvalidPlaneException;
    public List<PlaneStatusDto> getPlanes();
}
