package com.example.aiport.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airports")
public class AirportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;

    @OneToMany
    @JoinColumn(name = "airport_id",referencedColumnName = "id")
    private List<AirPlansEntity> airPlansEntityList;

    @OneToMany
    @JoinColumn(name = "airport_id",referencedColumnName = "id")
    private List<FlightsEntity> flightsEntityList;

    public AirportEntity() {
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

    public List<AirPlansEntity> getAirPlansEntityList() {
        return airPlansEntityList;
    }

    public void setAirPlansEntityList(List<AirPlansEntity> airPlansEntityList) {
        this.airPlansEntityList = airPlansEntityList;
    }

    public List<FlightsEntity> getFlightsEntityList() {
        return flightsEntityList;
    }

    public void setFlightsEntityList(List<FlightsEntity> flightsEntityList) {
        this.flightsEntityList = flightsEntityList;
    }
}
