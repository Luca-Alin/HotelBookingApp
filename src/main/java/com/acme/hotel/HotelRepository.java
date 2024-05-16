package com.acme.hotel;

import com.acme.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("""
            SELECT h
            FROM Hotel h
            WHERE (6371 * acos(cos(radians(?1))
            * cos(radians(h.latitude))
            * cos(radians(h.longitude)
            - radians(?2))
            + sin(radians(?1))
            * sin(radians(h.latitude))))
            <= ?3
    """)
    List<Hotel> findHotelsWithinRange(double clientLatitude, double clientLongitude, int maxRange);

    @Query("""
            select h
            from Hotel h
            join h.rooms r
            where r = ?1
    """)
    Optional<Hotel> findHotelByRoom(Room room);
}
