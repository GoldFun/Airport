package com.example.aiport.dto;

import com.example.aiport.entity.AirportEntity;

public class FlightsDto {
    public FlightsDto() {
    }
    private Long id;
    private String title;
    private Long placeOfDeparture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(Long placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }
}
