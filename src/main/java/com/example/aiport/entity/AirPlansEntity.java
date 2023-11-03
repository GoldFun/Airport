package com.example.aiport.entity;

import javax.persistence.*;

@Entity
@Table(name = "air_plans")
public class AirPlansEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "model")
    private String model;
    @Column(name = "marka")
    private String marka;
    @Column(name = "countPlace")
    private Integer countPlace;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "airport_id",referencedColumnName = "id")
    private AirportEntity airportEntity;

    @OneToOne
    @JoinColumn(name = "id")
    private FlightsEntity flightsEntity;





    public AirPlansEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Integer getCountPlace() {
        return countPlace;
    }

    public void setCountPlace(Integer countPlace) {
        this.countPlace = countPlace;
    }

    public AirportEntity getAirportEntity() {
        return airportEntity;
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

    public FlightsEntity getFlightsEntity() {
        return flightsEntity;
    }



    public void setFlightsEntity(FlightsEntity flightsEntity) {
        this.flightsEntity = flightsEntity;
    }
}
