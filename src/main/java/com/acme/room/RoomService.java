package com.acme.room;

import com.acme.hotel.Hotel;
import com.acme.hotel.HotelRepository;
import com.acme.reservation.Reservation;
import com.acme.reservation.ReservationRepository;
import com.acme.user.User;
import com.acme.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public List<Room> findAllByHotelId(int hotelId) {
        return roomRepository.findAllByHotel_Id(hotelId);
    }

    public void deleteRoomById(UserDetails userDetails, int roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Hotel hotel = hotelRepository.findById(room.getHotel().getId()).orElseThrow();

        if (!userDetails.getUsername().equals(hotel.getHotelOwner().getUsername())) {
            throw new BadCredentialsException("Invalid hotel owner.");
        }

        roomRepository.deleteById(roomId);
    }

    public Room saveRoom(UserDetails userDetails, Room room, int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();

        if (!userDetails.getUsername().equals(hotel.getHotelOwner().getUsername())) {
            throw new BadCredentialsException("Invalid hotel owner.");
        }

        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);

        hotel.getRooms().add(savedRoom);
        hotelRepository.save(hotel);

        return savedRoom;
    }

    public Room update(UserDetails userDetails, Room room) {
        Hotel hotel = hotelRepository.findHotelByRoom(room).orElseThrow();

        if (!userDetails.getUsername().equals(hotel.getHotelOwner().getUsername())) {
            throw new BadCredentialsException("Invalid hotel owner.");
        }

        return roomRepository.save(room);
    }

    public List<Room> bookRooms(UserDetails userDetails, List<Room> rooms) {
        User user = findUser(userDetails);

        List<Room> roomsFromDatabase = roomRepository.findAllById(
                rooms.stream().map(Room::getId).toList()
        );

        roomsFromDatabase.forEach(room -> room.setIsAvailable(false));

        List<Room> savedRooms = roomRepository.saveAll(roomsFromDatabase);

        List<Reservation> reservations = new ArrayList<>();
        for (Room room : savedRooms) {
            Reservation reservation = Reservation
                    .builder()
                    .room(room)
                    .user(user)
                    .build();
            reservations.add(reservation);
        }
        reservationRepository.saveAll(reservations);

        return savedRooms;
    }

    public Room cancelRoom(UserDetails userDetails, int roomId) {
        User user = findUser(userDetails);
        Room room = roomRepository.findById(roomId).orElseThrow();

        Reservation reservation = reservationRepository.findByUserAndRoom(user, room).orElseThrow();
        final LocalDateTime currentTimeMinus2Hours = LocalDateTime.now().minusHours(2);
        if (currentTimeMinus2Hours.isAfter(reservation.getStartDate())) {
            throw new RuntimeException("Reservation canceling date has expired.");
        }

        reservationRepository.deleteByUserAndRoom(user, room);
        room.setIsAvailable(true);
        roomRepository.save(room);

        return room;
    }

    private User findUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
    }
}
