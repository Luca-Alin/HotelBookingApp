package com.acme.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Room>> getAllRoomsByHotelId(@PathVariable Integer id) {
        try {
            List<Room> rooms = roomService.findAllByHotelId(id);
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error has occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeRoom(@AuthenticationPrincipal UserDetails userDetails,  @PathVariable Integer id) {
        try {
            roomService.deleteRoomById(userDetails, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error has occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{hotelId}")
    public ResponseEntity<Room> saveRoom(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Room room, @PathVariable int hotelId) {
        try {
            Room savedRoom = roomService.saveRoom(userDetails, room, hotelId);
            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity<Room> updateRoom(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Room room) {
        try {
            Room updatedRoom = roomService.update(userDetails, room);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error has occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book-room")
    public ResponseEntity<List<Room>> bookRooms(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<Room> rooms) {
        try {
            List<Room> bookedRooms = roomService.bookRooms(userDetails, rooms);
            return new ResponseEntity<>(bookedRooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error has occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancel-rooms/{id}")
    public ResponseEntity<Room> cancelBookingRooms(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        try {
            Room room = roomService.cancelRoom(userDetails, id);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error has occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
