package fiveman.hotelservice.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.NullableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullFields;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import fiveman.hotelservice.entities.Device;
import fiveman.hotelservice.entities.OverviewService;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.OverviewServiceRepository;
import fiveman.hotelservice.service.OverviewServiceService;
import fiveman.hotelservice.utils.Common;
import fiveman.hotelservice.utils.Utilities;

@Service
public class OverviewServiceServiceImpl implements OverviewServiceService{

	@Autowired
	OverviewServiceRepository overviewServiceRepository;

	@Override
	public List<OverviewService> getAllOverviewService() {
		return overviewServiceRepository.findAll();
	}

	@Override
	public OverviewService getOverviewService(long id) {
		OverviewService overviewService = overviewServiceRepository.findOverviewServiceById(id);
		if(overviewService != null) {
			return overviewService;
		}else{
			throw new AppException(HttpStatus.NOT_FOUND.value(), "Not found id = "+id);
		}
	}

	@Override
	public OverviewService addOverviewService(OverviewService newOverviewService) {
		if(overviewServiceRepository.findOverviewServiceById(newOverviewService.getId()) != null){
			throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "The Id is Exist");
		}else{
			String description = newOverviewService.getDescription() == null ? Common.OVERVIEW_DESCRIPTION : newOverviewService.getDescription();
			String imageURL = newOverviewService.getImageUrl() == null ? Common.OVERVIEW_IMAGE_URL : newOverviewService.getImageUrl();
			String title = newOverviewService.getTitle() == null ? Common.OVERVIEW_TITLE: newOverviewService.getTitle();
			newOverviewService.setId(0);
			newOverviewService.setDescription(description);
			newOverviewService.setImageUrl(imageURL);
			newOverviewService.setTitle(title);
			overviewServiceRepository.save(newOverviewService);
			return newOverviewService;
		}
	}


	//This for check Overview fields is not null
	//Resist the duplicate code for checking
	OverviewService checkOverview(OverviewService newOverviewService){
		OverviewService oldOverviewService = overviewServiceRepository.findOverviewServiceById(newOverviewService.getId());
		String imageURL = Utilities.isEmptyString(newOverviewService.getImageUrl()) ? oldOverviewService.getImageUrl() : newOverviewService.getImageUrl();
		String title = Utilities.isEmptyString(newOverviewService.getTitle()) ? oldOverviewService.getTitle() : newOverviewService.getTitle();
		String description =  Utilities.isEmptyString(newOverviewService.getDescription()) ? oldOverviewService.getDescription() : newOverviewService.getDescription();
		oldOverviewService.setDescription(description);
		oldOverviewService.setImageUrl(imageURL);
		oldOverviewService.setTitle(title);
		return oldOverviewService;
	}

	@Override
	public OverviewService updateOverviewService(OverviewService newOverviewService) {
		OverviewService oldOverviewService = overviewServiceRepository.findOverviewServiceById(newOverviewService.getId());
		if( oldOverviewService!= null){
			oldOverviewService = checkOverview(newOverviewService);
			overviewServiceRepository.save(oldOverviewService);
			return overviewServiceRepository.findOverviewServiceById(newOverviewService.getId());
		}else{
			throw new AppException(HttpStatus.NOT_FOUND.value(), "Not found id = " + newOverviewService.getId());
		}
	}

	@Override
	public boolean deleteOverviewService(long id) {
		OverviewService overviewService = overviewServiceRepository.findOverviewServiceById(id);
		if(overviewService != null){
			overviewServiceRepository.deleteById(id);
			return true;
		}else{
			throw new AppException(HttpStatus.NOT_FOUND.value(), "Not found id = " + id);
		}
	}
	
	
	
}
