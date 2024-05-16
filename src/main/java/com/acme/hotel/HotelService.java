package com.acme.hotel;

import com.acme.user.User;
import com.acme.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel saveHotel(UserDetails userDetails, Hotel hotel) {
        User user = getUser(userDetails);
        hotel.setHotelOwner(user);

        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(UserDetails userDetails, Hotel hotel) {
        User user = getUser(userDetails);

        Hotel hotelToUpdate = hotelRepository.findById(hotel.getId()).orElseThrow();
        if (!user.getEmail().equals(hotel.getHotelOwner().getEmail())) {
            throw new BadCredentialsException("Invalid hotel owner");
        }

        return hotelRepository.save(hotel);
    }

    public void deleteHotel(UserDetails userDetails, Integer id) {
        User user = getUser(userDetails);

        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        if (!user.getEmail().equals(hotel.getHotelOwner().getEmail())) {
            throw new BadCredentialsException("Invalid hotel owner");
        }

        hotelRepository.delete(hotel);
    }


    public List<Hotel> findHotelsWithinRange(double clientLatitude, double clientLongitude, int maxRange) {
        return hotelRepository.findHotelsWithinRange(clientLatitude, clientLongitude, maxRange);
    }

    public User getUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    }
}
