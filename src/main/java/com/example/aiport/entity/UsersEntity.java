package com.example.aiport.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersEntity  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "surname")
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<RolesEntity> rolesEntity = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "airport_id")
    private AirportEntity airportEntity;

    @ManyToOne
    @JoinColumn(name = "flights_id")
    private FlightsEntity flightsEntity;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private AirPlansEntity airPlansEntity;



    public UsersEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.rolesEntity;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<RolesEntity> getRolesEntity() {
        return rolesEntity;
    }

    public void setRolesEntity(List<RolesEntity> rolesEntity) {
        this.rolesEntity = rolesEntity;
    }

    public AirportEntity getAirportEntity() {
        return airportEntity;
    }

    public void setAirportEntity(AirportEntity airportEntity) {
        this.airportEntity = airportEntity;
    }

    public FlightsEntity getFlightsEntity() {
        return flightsEntity;
    }
    public AirPlansEntity getAirPlansEntity() {
        return airPlansEntity;
    }

    public void setAirPlansEntity(AirPlansEntity airPlansEntity) {
        this.airPlansEntity = airPlansEntity;
    }

    public void setFlightsEntity(FlightsEntity flightsEntity) {
        this.flightsEntity = flightsEntity;
    }
}


