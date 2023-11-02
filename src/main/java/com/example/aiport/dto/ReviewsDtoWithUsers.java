package com.example.aiport.dto;

public class ReviewsDtoWithUsers {
    private String message;
    private Long idFlight;
    private String login;

    public ReviewsDtoWithUsers() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(Long idFlight) {
        this.idFlight = idFlight;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
