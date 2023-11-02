package com.example.aiport.dto;

import java.time.LocalDateTime;

public class HistoryPastFlightsDto {
    private String title;
    private String placeOfDeparture;
    private String airport;
    private String date;

    public HistoryPastFlightsDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(String placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
