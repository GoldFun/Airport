package com.example.aiport.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class FlightsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "place_of_departure")
    private AirportEntity placeOfDeparture;

    @OneToOne
    @JoinColumn(name = "airplane_id",referencedColumnName = "id")
    private AirPlansEntity airPlansEntity;


    @ManyToOne
    @JoinColumn(name = "airport_id",referencedColumnName = "id")
    private AirportEntity airportEntity;


    public FlightsEntity() {
    }

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



    public AirPlansEntity getAirPlansEntity() {
        return airPlansEntity;
    }

    public void setAirPlansEntity(AirPlansEntity airPlansEntity) {
        this.airPlansEntity = airPlansEntity;
    }

    public AirportEntity getAirportEntity() {
        return airportEntity;
    }

    public AirportEntity getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(AirportEntity placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public void setAirportEntity(AirportEntity airportEntity) {
        this.airportEntity = airportEntity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
