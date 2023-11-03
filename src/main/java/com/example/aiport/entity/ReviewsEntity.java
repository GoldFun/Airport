package com.example.aiport.entity;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class ReviewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "flights_id")
    private FlightsEntity flights;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;
    public ReviewsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FlightsEntity getFlights() {
        return flights;
    }

    public void setFlights(FlightsEntity flights) {
        this.flights = flights;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
