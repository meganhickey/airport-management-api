package com.airportmanagement.rest.aircraft;

import com.airportmanagement.rest.airport.Airport;
import com.airportmanagement.rest.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String airlineName;
    private int numberOfPassengers;

    @ManyToMany
    @JsonIgnoreProperties("aircraft")
    private List<Passenger> passengers = new ArrayList<Passenger>();

    @ManyToMany
    @JsonIgnoreProperties("aircraft")
    private List<Airport> airports = new ArrayList<Airport>();

    public Aircraft() {}

    public Aircraft(String type, String airlineName, int numberOfPassengers, List<Passenger> passengers, List<Airport> airports) {
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
        this.passengers = passengers;
        this.airports = airports;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
