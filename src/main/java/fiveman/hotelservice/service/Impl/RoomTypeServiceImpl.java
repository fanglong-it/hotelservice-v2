package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.RoomType;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.RoomTypeRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.RoomTypeService;
import fiveman.hotelservice.utils.Common;
import fiveman.hotelservice.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

//    Logger logger = LoggerFactory.getLogger(RoomTypeServiceImpl.class);
    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> findAllRoomType() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getRoomType(long id) {
        if(roomTypeRepository.existsById(id)){
            return roomTypeRepository.getById(id);
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(), new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Not found RoomType Id = " + id));
    }

    RoomType checkRoomType(RoomType roomType){
        if(Utilities.isEmptyString(roomType.getDescription())){
            roomType.setDescription(Common.ROOM_TYPE_DESCRIPTION);
        }
        if(Utilities.isEmptyString(roomType.getPicture())){
            roomType.setPicture(Common.ROOM_TYPE_IMAGE_URL);
        }
        if(Utilities.isEmptyString(roomType.getName())){
            roomType.setName(Common.ROOM_TYPE_NAME);
        }
        return roomType;
    }

    @Override
    public RoomType addRoomType(RoomType roomType) {
        if(!roomTypeRepository.existsById(roomType.getId())){
        RoomType roomTypeChecked =  checkRoomType(roomType);
        return roomTypeRepository.save(roomTypeChecked);
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(), new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(), "The roomType id is Exist = " + roomType.getId()));
    }

    @Override
    public RoomType updateRoomType(RoomType roomType) {
        return null;
    }

    @Override
    public String deleteRoomType(long id) {
        if(roomTypeRepository.existsById(id)){
            roomTypeRepository.delete(roomTypeRepository.getById(id));
            return "Delete Success";
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(), new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(), "Not found id =" +id));

    }
}