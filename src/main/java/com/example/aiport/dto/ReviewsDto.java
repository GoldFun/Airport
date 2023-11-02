package com.example.aiport.dto;

public class ReviewsDto {
    private String message;
    private Long idFlight;
    public ReviewsDto() {
    }

    public Long getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Long idFlight) {
        this.idFlight = idFlight;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
