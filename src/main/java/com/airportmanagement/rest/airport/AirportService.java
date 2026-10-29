package com.airportmanagement.rest.airport;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Iterable<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport getAirportById(Long id) {
        return airportRepository.findById(id).orElseThrow();
    }

    public Airport updateAirport(Long id, Airport updatedAirport) {
        Airport airport = airportRepository.findById(id).orElseThrow();

        airport.setName(updatedAirport.getName());
        airport.setCity(updatedAirport.getCity());
        airport.setCode(updatedAirport.getCode());

        return airportRepository.save(airport);
    }

    public void deleteAirportById(Long id) {
        airportRepository.deleteById(id);
    }

}