package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.Picture;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.PictureRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public List<Picture> getAllPicture() {
        return pictureRepository.findAll();
    }

    @Override
    public List<Picture> getPictureByPictureType(String type) {
        return pictureRepository.findAllByPictureType(type);
    }

    @Override
    public Picture savePicture(Picture picture) {
        if(!pictureRepository.existsById(picture.getId())){
            return pictureRepository.save(picture);
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(), new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(), "Existed id = " + picture.getId()));
    }

    @Override
    public Picture getPictureById(Long id) {
        return pictureRepository.getPictureById(id);
    }

    @Override
    public Picture updatePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public String deletePicture(Long id) {
        if(pictureRepository.existsById(id)){
            pictureRepository.deleteById(id);
            return "Deleted";
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(), new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Not found id = " + id));
    }
}
