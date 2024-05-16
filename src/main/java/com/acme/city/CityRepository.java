package com.acme.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    @Query("""
        select distinct c.country
        from City c
    """)
    List<String> findAllCountryNames();

    List<City> findByCountry(String country);
}
