package com.acme.city;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/all-countries")
    public Iterable<String> findAllCountries() {
        return cityRepository.findAllCountryNames();
    }

    @GetMapping("/{country}")
    public Iterable<City> findCityByCountry(@PathVariable String country) {
        return cityRepository.findByCountry(country);
    }
}
