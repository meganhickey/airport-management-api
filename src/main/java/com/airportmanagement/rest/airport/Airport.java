package com.airportmanagement.rest.airport;
import com.airportmanagement.rest.aircraft.Aircraft;
import com.airportmanagement.rest.city.City;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private City city;

    private String name;
    private String code;

    @ManyToMany(mappedBy = "airports")
    private List<Aircraft> aircraft = new ArrayList<Aircraft>();

    public Airport() {
    }

    public Airport(City city, String name, String code) {
        this.city = city;
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }
}