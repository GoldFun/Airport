package com.example.aiport.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_past_flights")
public class HistoryPastFlightsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity usersEntity;
    @ManyToOne
    @JoinColumn(name = "flights_id")
    private FlightsEntity flights;
    public HistoryPastFlightsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public FlightsEntity getFlights() {
        return flights;
    }

    public void setFlights(FlightsEntity flights) {
        this.flights = flights;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
