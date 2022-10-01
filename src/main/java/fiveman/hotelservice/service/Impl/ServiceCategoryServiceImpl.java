package fiveman.hotelservice.service.Impl;

import fiveman.hotelservice.entities.ServiceCategory;
import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.ServiceCategoryRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public ServiceCategory getServiceCategoryById(Long id) {
        return serviceCategoryRepository.getServiceCategoryById(id);
    }

    @Override
    public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
        if(!serviceCategoryRepository.existsById(serviceCategory.getId())){
            return serviceCategoryRepository.save(serviceCategory);
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(),
                new CustomResponseObject(HttpStatus.ALREADY_REPORTED.toString(),
                        "Exist id = " + serviceCategory.getId()));
    }


    @Override
    public List<ServiceCategory> getServiceCategories() {
        return serviceCategoryRepository.findAll();
    }
}
