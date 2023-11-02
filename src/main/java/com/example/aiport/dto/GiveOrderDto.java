package com.example.aiport.dto;

public class GiveOrderDto {
    private Long id;
    private String planeStatus;
    public GiveOrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaneStatus() {
        return planeStatus;
    }

    public void setPlaneStatus(String planeStatus) {
        this.planeStatus = planeStatus;
    }
}
