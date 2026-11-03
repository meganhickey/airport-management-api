package com.airportmanagement.rest.aircraft;

import com.airportmanagement.rest.airport.Airport;
import com.airportmanagement.rest.airport.AirportRepository;
import com.airportmanagement.rest.passenger.Passenger;
import com.airportmanagement.rest.passenger.PassengerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final PassengerRepository passengerRepository;
    private final AirportRepository airportRepository;

    public AircraftService(AircraftRepository aircraftRepository, PassengerRepository passengerRepository, AirportRepository airportRepository) {
        this.aircraftRepository = aircraftRepository;
        this.passengerRepository = passengerRepository;
        this.airportRepository = airportRepository;
    }

    public Page<Aircraft> getAircraftPaged(Pageable pageable) {
        return aircraftRepository.findAll(pageable);
    }

    public Iterable<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    public Aircraft getAircraftById(Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aircraft not found."));
    }

    public Aircraft saveAircraft(Aircraft aircraft) {
        validateAndSetAircraftInfo(aircraft);

        return aircraftRepository.save(aircraft);
    }

    public Aircraft updateAircraft(Long id, Aircraft updatedAircraft) {
        Aircraft aircraft = getAircraftById(id);

        aircraft.setType(updatedAircraft.getType());
        aircraft.setAirlineName(updatedAircraft.getAirlineName());
        aircraft.setNumberOfPassengers(updatedAircraft.getNumberOfPassengers());
        aircraft.setPassengers(updatedAircraft.getPassengers());
        aircraft.setAirports(updatedAircraft.getAirports());

        validateAndSetAircraftInfo(aircraft);

        return aircraftRepository.save(aircraft);
    }

    private void validateAndSetAircraftInfo(Aircraft aircraft) {
        List<Airport> airportList = new ArrayList<>();

        for (Airport airport : aircraft.getAirports()) {
            Airport existingAirport = airportRepository.findById(airport.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Airport not found."));

            airportList.add(existingAirport);
        }

        aircraft.setAirports(airportList);

        List<Passenger> passengerList = new ArrayList<>();

        for (Passenger passenger : aircraft.getPassengers()) {
            Passenger existingPassenger = passengerRepository.findById(passenger.getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Passenger not found."));

            passengerList.add(existingPassenger);
        }

        aircraft.setPassengers(passengerList);
    }

    public void deleteAircraft(Long id) {
        Aircraft aircraft = getAircraftById(id);
        aircraftRepository.delete(aircraft);
    }
}
