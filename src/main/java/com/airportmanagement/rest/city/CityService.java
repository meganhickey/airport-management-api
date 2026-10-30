package com.airportmanagement.rest.city;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City not found"));
    }

    public City updateCity(Long id, City updatedCity) {
        City city = getCityById(id);
        city.setName(updatedCity.getName());
        city.setProvince(updatedCity.getProvince());
        city.setPopulation(updatedCity.getPopulation());

        return cityRepository.save(city);
    }

    public void deleteCityById(Long id) {
        getCityById(id);
        cityRepository.deleteById(id);
    }
}
