package com.acme.city;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String city;
    private String city_ascii;
    private Double lat;
    private Double lng;
    private String country;
    private String iso2;
    private String iso3;
    private String admin_name;
    private String capital;
    private String population;
    private String id;
}
