package com.example.aiport.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "readings")
public class ReadingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "count_clients_used_services")
    private Integer countClientsUsedServices;
    @Column(name = "count_flight_used")
    private Integer countFlightUsed;
    @Column(name = "date")
    private LocalDate date;
    public ReadingsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
