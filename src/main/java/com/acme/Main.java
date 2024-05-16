package com.acme;

import com.acme.city.City;
import com.acme.city.CityRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner(
            final CityRepository cityRepository,
            final ResourceLoader resourceLoader) {
        return _ -> {
            if (cityRepository.count() != 0)
                return;

            Resource resource = resourceLoader.getResource("classpath:data/worldcities.csv");
            InputStreamReader isr = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            br.readLine(); // skip the line with column names
            String data = null;

            while ((data = br.readLine()) != null) {
                String[] tokens = data.split(",");
                tokens = Arrays.stream(tokens).map(token -> token.substring(1, token.length() - 1)).toArray(String[]::new);
                City city = new City(
                        null,
                        tokens[0],
                        tokens[1],
                        Double.parseDouble(tokens[2]),
                        Double.parseDouble(tokens[3]),
                        tokens[4],
                        tokens[5],
                        tokens[6],
                        tokens[7],
                        tokens[8],
                        tokens[9],
                        tokens[10]
                );
                cityRepository.save(city);
            }
        };
    }
}
