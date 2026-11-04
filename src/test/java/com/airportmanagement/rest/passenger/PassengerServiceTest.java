
package com.airportmanagement.rest.passenger;

import com.airportmanagement.rest.city.City;
import com.airportmanagement.rest.city.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    public void testGetAllPassengers() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");

        List<Passenger> passengers = List.of(passenger);

        Mockito.when(passengerRepository.findAll())
                .thenReturn(passengers);

        Iterable<Passenger> result =
                passengerService.getAllPassengers();

        Assertions.assertEquals(passengers, result);
    }

    @Test
    public void testGetPassengerById() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");

        Mockito.when(passengerRepository.findById(1L))
                .thenReturn(Optional.of(passenger));

        Passenger result = passengerService.getPassengerById(1L);

        Assertions.assertEquals("John", result.getFirstName());
    }

    @Test
    public void testGetPassengerByIdNotFound() {
        Mockito.when(passengerRepository.findById(99L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> passengerService.getPassengerById(99L)
        );
    }

    @Test
    public void testSavePassenger() {
        City city = Mockito.mock(City.class);

        Mockito.when(city.getId()).thenReturn(2L);
        Mockito.when(city.getName()).thenReturn("St. John's");

        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Smith");
        passenger.setCity(city);

        Mockito.when(cityRepository.findById(2L))
                .thenReturn(Optional.of(city));

        Mockito.when(passengerRepository.save(passenger))
                .thenReturn(passenger);

        Passenger result = passengerService.savePassenger(passenger);

        Assertions.assertEquals("John", result.getFirstName());
        Assertions.assertEquals("St. John's",
                result.getCity().getName());

        Mockito.verify(passengerRepository, Mockito.times(1))
                .save(passenger);
    }
}
