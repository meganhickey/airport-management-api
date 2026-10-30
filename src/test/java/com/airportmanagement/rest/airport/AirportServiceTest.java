package com.airportmanagement.rest.airport;

import com.airportmanagement.rest.city.City;
import com.airportmanagement.rest.city.CityRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private AirportService airportService;

    @Test
    void testGetAllAirports() {
        // Create a sample city
        City city = new City("St. John's", "Newfoundland and Labrador", 110000);

        // Mock the repository to return a list of airports
        Iterable<Airport> mockAirports = List.of(
                new Airport(city, "St. John's International Airport", "YYT")
        );

        when(airportRepository.findAll()).thenReturn(mockAirports);

        // Call the service method
        Iterable<Airport> result = airportService.getAllAirports();

        // Verify that the result matches the mocked data
        assertIterableEquals(mockAirports, result);
    }

    @Test
    void testGetAirportById() {
        // Create a sample city
        City city = new City("St. John's", "Newfoundland and Labrador", 110000);

        // Create a sample airport
        Airport mockAirport = new Airport(city, "St. John's International Airport", "YYT");

        // Mock the repository to return the sample airport
        when(airportRepository.findById(1L)).thenReturn(Optional.of(mockAirport));

        // Call the service method
        Optional<Airport> result = Optional.ofNullable(airportService.getAirportById(1L));

        // Verify that the result matches the mocked data
        assertTrue(result.isPresent());
        assertEquals(mockAirport, result.get());
    }

    @Test
    void testGetAirportByIdNotFound() {
        // Mock the repository to return no airport
        when(airportRepository.findById(999L)).thenReturn(Optional.empty());

        // Verify that the service throws an exception
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> airportService.getAirportById(999L)
        );

        // Check that the response status is 404
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testSaveAirportWithoutCityId() {
        // Create a city without an ID
        City city = new City("St. John's", "Newfoundland and Labrador", 110000);

        // Create an airport referencing that city
        Airport airport = new Airport(city, "St. John's International Airport", "YYT");

        // Verify that saving the airport throws an exception
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> airportService.saveAirport(airport)
        );

        // Check that the response status is 400
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
}
