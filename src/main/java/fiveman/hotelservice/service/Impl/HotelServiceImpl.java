package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.Hotel;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.HotelRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.getHotelById(id);
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        if(!hotelRepository.existsById(hotel.getId())){
            return hotelRepository.save(hotel);
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(), new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(), "Existed id = " + hotel.getId()));
    }

    @Override
    public Hotel updateHotel(Hotel hotel) {
        if(hotelRepository.existsById(hotel.getId())){
            return hotelRepository.save(hotel);
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(), new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Not found id = " + hotel.getId()));

    }

    @Override
    public String deleteHotel(Long id) {
        if(hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return "deleted";
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(), new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Notfound id = " + id));
    }
}
