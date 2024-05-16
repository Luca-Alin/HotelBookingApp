package com.acme.hotel;

import com.acme.room.Room;
import com.acme.user.User;
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
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private double latitude;
    private double longitude;

    @OneToMany
    private List<Room> rooms;

    @ManyToOne
    private User hotelOwner;

    public List<Room> getRooms() {
        if (rooms != null) {
            rooms = new ArrayList<>();
        }
        return rooms;
    }
}
