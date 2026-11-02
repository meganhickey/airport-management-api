package com.airportmanagement.rest.passenger;

import com.airportmanagement.rest.aircraft.AircraftRepository;
import com.airportmanagement.rest.city.City;
import com.airportmanagement.rest.city.CityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final CityRepository cityRepository;

    public PassengerService(PassengerRepository passengerRepository, CityRepository cityRepository) {
        this.passengerRepository = passengerRepository;
        this.cityRepository = cityRepository;
    }

    public Page<Passenger> getAllPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }

    public Iterable<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger savePassenger(Passenger passenger) {
        validateAndSetPassengerInfo(passenger);

        return passengerRepository.save(passenger);
    }

    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Passenger not found."));
    }

    public Passenger updatePassenger(Long id, Passenger updatedPassenger) {
        Passenger passenger = getPassengerById(id);

        passenger.setFirstName(updatedPassenger.getFirstName());
        passenger.setLastName(updatedPassenger.getLastName());
        passenger.setPhoneNumber(updatedPassenger.getPhoneNumber());
        passenger.setCity(updatedPassenger.getCity());

        validateAndSetPassengerInfo(passenger);

        return passengerRepository.save(passenger);
    }

    private void validateAndSetPassengerInfo(Passenger passenger) {
        City existingCity = cityRepository.findById(passenger.getCity().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found."));

        passenger.setCity(existingCity);
    }

    public void deletePassenger(Long id) {
        getPassengerById(id);
        passengerRepository.deleteById(id);
    }
}
