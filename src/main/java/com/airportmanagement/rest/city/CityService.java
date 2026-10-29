package com.airportmanagement.rest.city;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City updateCity(Long id, City updatedCity) {
        City city = cityRepository.findById(id).orElseThrow();

        city.setName(updatedCity.getName());
        city.setProvince(updatedCity.getProvince());
        city.setPopulation(updatedCity.getPopulation());

        return cityRepository.save(city);
    }

    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }
}
