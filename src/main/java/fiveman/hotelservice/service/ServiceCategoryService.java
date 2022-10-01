package fiveman.hotelservice.service;

import fiveman.hotelservice.entities.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {

    List<ServiceCategory> getServiceCategories();
    ServiceCategory getServiceCategoryById(Long id);
    ServiceCategory saveServiceCategory(ServiceCategory serviceCategory);

}
