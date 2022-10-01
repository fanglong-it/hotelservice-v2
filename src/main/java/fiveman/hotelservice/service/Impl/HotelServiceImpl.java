package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.Hotel;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.HotelRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        log.info("START GETTING ALL HOTEL");
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(Long id) {
        log.info("START GETTING HOTEL BY ID");
        return hotelRepository.getHotelById(id);
    }

    @Override
    public CustomResponseObject saveHotel(Hotel hotel) {
        if(!hotelRepository.existsById(hotel.getId())){
            log.info("START SAVING HOTEL");
            hotelRepository.save(hotel);
            return new CustomResponseObject(HttpStatus.CREATED.toString(),
                    "Create success!");
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(),
                new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(),
                        "Existed id = " + hotel.getId()));
    }

    @Override
    public CustomResponseObject updateHotel(Hotel hotel) {
        if(hotelRepository.existsById(hotel.getId())){
            log.info("START UPDATE HOTEL");
            hotelRepository.save(hotel);
            return new CustomResponseObject(HttpStatus.CREATED.toString(),
                    "Update success!");
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(),
                new CustomResponseObject(HttpStatus.NOT_FOUND.toString(),
                        "Not found id = " + hotel.getId()));

    }

    @Override
    public CustomResponseObject deleteHotel(Long id) {
        if(hotelRepository.existsById(id)){
            log.info("START DELETE HOTEL");
            hotelRepository.deleteById(id);
            return new CustomResponseObject(HttpStatus.CREATED.toString(),
                    "Delete success!");
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(),
                new CustomResponseObject(HttpStatus.NOT_FOUND.toString(),
                        "Notfound id = " + id));
    }
}
