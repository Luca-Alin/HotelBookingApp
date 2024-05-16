package com.acme.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("""
            select h
            from Hotel h
            where (6371 * acos(cos(radians(?1))
                * cos(radians(h.latitude))
                * cos(radians(h.longitude)
                - radians(?2))
                + sin(radians(?3))
                * sin(radians(h.latitude)))) <= :maxRange
            """)
    List<Hotel> findHotelsWithinRange(double clientLatitude, double clientLongitude, int maxRange);
}
