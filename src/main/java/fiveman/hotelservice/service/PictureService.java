package fiveman.hotelservice.service;

import fiveman.hotelservice.entities.Picture;
import fiveman.hotelservice.entities.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> getAllPicture();
    List<Picture> getPictureByPictureType(String type);
    Picture savePicture(Picture picture);
    Picture getPictureById(Long id);
    Picture updatePicture(Picture picture);
    String deletePicture(Long id);
}
