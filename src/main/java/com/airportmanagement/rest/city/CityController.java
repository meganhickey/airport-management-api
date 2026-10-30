package com.airportmanagement.rest.city;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequestMapping("/api/cities")
@RestController
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Iterable<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/paged")
    public Page<City> getCitiesPaged(Pageable pageable) {
        return cityService.getAllCities(pageable);
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.saveCity(city);
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCityById(id);
    }

    @PutMapping("/{id}")
    public City updateCity(@PathVariable Long id, @RequestBody City updatedCity) {
        return cityService.updateCity(id, updatedCity);
    }
}
