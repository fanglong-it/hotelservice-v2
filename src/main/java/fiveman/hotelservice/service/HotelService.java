package fiveman.hotelservice.service;

import fiveman.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(Long id);
    Hotel saveHotel(Hotel hotel);
    Hotel updateHotel(Hotel hotel);
    String deleteHotel(Long id);
}
