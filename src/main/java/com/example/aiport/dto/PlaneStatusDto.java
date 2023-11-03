package com.example.aiport.dto;

import com.example.aiport.entity.atribute.PlaneStatus;

public class PlaneStatusDto {
    private Long id;
    private String model;
    private String marka;
    private Integer countPlace;
    private PlaneStatus planeStatus;
    public PlaneStatusDto() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Integer getCountPlace() {
        return countPlace;
    }

    public void setCountPlace(Integer countPlace) {
        this.countPlace = countPlace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaneStatus getPlaneStatus() {
        return planeStatus;
    }

    public void setPlaneStatus(PlaneStatus planeStatus) {
        this.planeStatus = planeStatus;
    }
}
