package com.example.aiport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "requests_taking_plane")
public class RequestsTakingPlaneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "modelplane")
    private String modelPlane;
    @ManyToOne
    @JoinColumn(name = "airport_id")
    @JsonIgnore
    private AirportEntity airportEntity;

    @OneToOne
    @JoinColumn(name = "flights_id")
    private FlightsEntity flights;
    @ManyToOne
    @JoinColumn(name = "placeofdeparture")
    @JsonIgnore
    private AirportEntity placeOfDeparture;
    public RequestsTakingPlaneEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelPlane() {
        return modelPlane;
    }

    public void setModelPlane(String modelPlane) {
        this.modelPlane = modelPlane;
    }

    public AirportEntity getAirportEntity() {
        return airportEntity;
    }

    public void setAirportEntity(AirportEntity airportEntity) {
        this.airportEntity = airportEntity;
    }

    public AirportEntity getPlaceOfDeparture() {
        return placeOfDeparture;
    }

    public void setPlaceOfDeparture(AirportEntity placeOfDeparture) {
        this.placeOfDeparture = placeOfDeparture;
    }

    public FlightsEntity getFlights() {
        return flights;
    }

    public void setFlights(FlightsEntity flights) {
        this.flights = flights;
    }
}
