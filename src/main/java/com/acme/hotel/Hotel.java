package com.acme.hotel;

import com.acme.room.Room;
import com.acme.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Double latitude;
    private Double longitude;

    @OneToMany
    private List<Room> rooms;

    @ManyToOne
    private User hotelOwner;
}
