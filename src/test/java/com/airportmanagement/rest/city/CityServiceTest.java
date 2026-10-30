package com.airportmanagement.rest.city;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityService cityService;

    @Test
    void testGetAllCities() {
        // Mock the repository to return a list of cities
        Iterable<City> mockCities = List.of(
                new City("Mount Pearl", "Newfoundland and Labrador",22000)
        );

        when(cityRepository.findAll()).thenReturn(mockCities);

        // Call the service method
        Iterable<City> result = cityService.getAllCities();

        // Verify that the result matches the mocked data
        assertIterableEquals(mockCities, result);
    }

    @Test
    void testSaveCity( ){
        // Create a new city
        City newCity = new City("St. John's", "Newfoundland and Labrador", 110000);

        // Mock the repository to return the saved city
        when(cityRepository.save(newCity)).thenReturn(newCity);

        // Call the service method
        City savedCity = cityService.saveCity(newCity);

        // Verify that the saved city matches the input city
        assertEquals(newCity.getName(), savedCity.getName());
    }

    @Test
    void testGetCityById() {
        // Create a city with a specific ID
        City city = new City("Corner Brook", "Newfoundland and Labrador", 20000);

        // Mock the repository to return the city when findById is called
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        // Call the service method
        City result = cityService.getCityById(1L);

        // Verify that the result matches the mocked city
        assertEquals(city.getName(), result.getName());
    }

    @Test
    void testGetCityByIdNotFound() {

        // Mock the repository to return an empty Optional when findById is called
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            cityService.getCityById(1L);
        });

        // Verify that the exception message is as expected
        assertTrue(exception.getMessage().contains("City not found"));
    }

}
