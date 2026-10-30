package com.airportmanagement.rest.airport;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AirportRepository extends JpaRepository<Airport, Long> {
}
