package com.acme.reservation;

import com.acme.room.Room;
import com.acme.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByUserAndRoom(User user, Room room);

    void deleteByUserAndRoom(User user, Room room);
}
