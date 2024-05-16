package com.acme.hotel;

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
@RequestMapping("/api/v1/hotel")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> findAll() {
        List<Hotel> hotels = hotelService.findAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{latitude}/{longitude}/{radius}")
    public ResponseEntity<List<Hotel>> findHotelNearMyLocation(@PathVariable double latitude, @PathVariable double longitude, @PathVariable int radius) {
        try {
            List<Hotel> hotels = hotelService.findHotelsWithinRange(latitude, longitude, radius);
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<Hotel> createHotel(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Hotel hotel) {
        try {
            Hotel saved = hotelService.saveHotel(userDetails, hotel);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Hotel> updateHotel(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Hotel hotel) {
        try {
            Hotel updated = hotelService.updateHotel(userDetails, hotel);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
        try {
            hotelService.deleteHotel(userDetails, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
