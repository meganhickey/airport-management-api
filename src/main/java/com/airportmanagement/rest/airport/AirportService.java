package com.airportmanagement.rest.airport;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.airportmanagement.rest.city.CityRepository;
import com.airportmanagement.rest.city.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportService(AirportRepository airportRepository, CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    public Page<Airport> getAirportsPaged(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    public Iterable<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport saveAirport(Airport airport) {
        if (airport.getCity() == null || airport.getCity().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City ID is required");
        }

        City city = cityRepository.findById(airport.getCity().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found"));

        airport.setCity(city);

        return airportRepository.save(airport);
    }

    public Airport getAirportById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Airport not found"));
    }

    public Airport updateAirport(Long id, Airport updatedAirport) {
        Airport airport = getAirportById(id);

        airport.setName(updatedAirport.getName());
        airport.setCity(updatedAirport.getCity());
        airport.setCode(updatedAirport.getCode());

        return saveAirport(airport);
    }

    public void deleteAirportById(Long id) {
        getAirportById(id);
        airportRepository.deleteById(id);
    }
}