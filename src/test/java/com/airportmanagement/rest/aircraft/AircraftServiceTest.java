
package com.airportmanagement.rest.aircraft;

import com.airportmanagement.rest.airport.Airport;
import com.airportmanagement.rest.airport.AirportRepository;
import com.airportmanagement.rest.passenger.Passenger;
import com.airportmanagement.rest.passenger.PassengerRepository;

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
public class AircraftServiceTest {

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AircraftService aircraftService;

    @Test
    public void testGetAllAircraft() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");

        List<Aircraft> aircraftList = List.of(aircraft);

        Mockito.when(aircraftRepository.findAll())
                .thenReturn(aircraftList);

        Iterable<Aircraft> result = aircraftService.getAllAircraft();

        Assertions.assertEquals(aircraftList, result);
    }

    @Test
    public void testGetAircraftById() {
        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");

        Mockito.when(aircraftRepository.findById(1L))
                .thenReturn(Optional.of(aircraft));

        Aircraft result = aircraftService.getAircraftById(1L);

        Assertions.assertEquals("Boeing 737", result.getType());
    }

    @Test
    public void testGetAircraftByIdNotFound() {
        Mockito.when(aircraftRepository.findById(99L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> aircraftService.getAircraftById(99L)
        );
    }

    @Test
    public void testSaveAircraft() {
        Airport airport = Mockito.mock(Airport.class);
        Mockito.when(airport.getId()).thenReturn(2L);

        Passenger passenger = Mockito.mock(Passenger.class);
        Mockito.when(passenger.getId()).thenReturn(1L);

        Airport existingAirport = new Airport();
        existingAirport.setName("St. John's International");

        Passenger existingPassenger = new Passenger();
        existingPassenger.setFirstName("John");

        Aircraft aircraft = new Aircraft();
        aircraft.setType("Boeing 737");
        aircraft.setAirlineName("Air Canada");
        aircraft.setAirports(List.of(airport));
        aircraft.setPassengers(List.of(passenger));

        Mockito.when(airportRepository.findById(2L))
                .thenReturn(Optional.of(existingAirport));

        Mockito.when(passengerRepository.findById(1L))
                .thenReturn(Optional.of(existingPassenger));

        Mockito.when(aircraftRepository.save(aircraft))
                .thenReturn(aircraft);

        Aircraft result = aircraftService.saveAircraft(aircraft);

        Assertions.assertEquals("Boeing 737", result.getType());

        Assertions.assertSame(
                existingAirport,
                result.getAirports().get(0)
        );

        Assertions.assertSame(
                existingPassenger,
                result.getPassengers().get(0)
        );

        Mockito.verify(aircraftRepository, Mockito.times(1))
                .save(aircraft);
    }
}
