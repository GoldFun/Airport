package com.example.aiport.dto;

public class ReadingDto {
    private Integer countClientsUsedServices;
    private Integer countFlightUsed;
    private String date;
    public ReadingDto() {
    }

    public Integer getCountClientsUsedServices() {
        return countClientsUsedServices;
    }

    public void setCountClientsUsedServices(Integer countClientsUsedServices) {
        this.countClientsUsedServices = countClientsUsedServices;
    }

    public Integer getCountFlightUsed() {
        return countFlightUsed;
    }

    public void setCountFlightUsed(Integer countFlightUsed) {
        this.countFlightUsed = countFlightUsed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
