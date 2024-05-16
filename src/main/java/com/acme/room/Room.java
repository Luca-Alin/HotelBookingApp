package com.acme.room;

import com.acme.hotel.Hotel;
import com.acme.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomNumber;
    private RoomType type;
    private Double price;
    private Boolean isAvailable;

    @ManyToOne
    private Hotel hotel;

    @OneToMany
    private List<Reservation> reservations;

    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return reservations;
    }
}
