package com.example.aiport.dto;

public class RequestTakingPlaneDto {
    private Long idFlights;
    private String modelPlane;
    private String placeOfDeparture;
    private String airport;
    public RequestTakingPlaneDto() {
    }

    public Long getIdFlights() {
        return idFlights;
    }

    public void setIdFlights(Long idFlights) {
        this.idFlights = idFlights;
    }

    public String getModelPlane() {
        return modelPlane;
    }

    public void setModelPlane(String modelPlane) {
        this.modelPlane = modelPlane;
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
}
