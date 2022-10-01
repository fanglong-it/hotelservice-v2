package fiveman.hotelservice.service.Impl;

import java.util.List;

import fiveman.hotelservice.service.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fiveman.hotelservice.exception.AppException;
import fiveman.hotelservice.repository.ServiceRepository;
import fiveman.hotelservice.response.CustomResponseObject;
import fiveman.hotelservice.service.ServiceService;
import fiveman.hotelservice.utils.Common;
import fiveman.hotelservice.utils.Utilities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<fiveman.hotelservice.entities.Service> getAllServices() {
        log.info("START OF GET ALL SERVICES!!!");
        return serviceRepository.findAll();
    }

    @Override
    public fiveman.hotelservice.entities.Service getServiceById(Long id) {
        log.info("START OF GET SERVICE BY ID");
        if (serviceRepository.existsById(id)) {
            return serviceRepository.getServiceById(id);
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(),
                new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Not found service by id = " + id));
    }


    @Override
    public fiveman.hotelservice.entities.Service updateService(fiveman.hotelservice.entities.Service service) {
        log.info("CHECKING ID FOR UPDATE SERVICE");
        if (serviceRepository.existsById(service.getId())) {
            log.info("ID IS EXIST START OF UPDATE SERVICE");
            serviceRepository.save(service);
            return serviceRepository.getServiceById(service.getId());
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(), new CustomResponseObject(HttpStatus.NOT_FOUND.toString(),
                "Not found service by id = " + service.getId()));
    }

    @Autowired
    ServiceCategoryService serviceCategoryService;

    @Override
    public String deleteService(Long id) {
        log.info("CHECKING ID FOR DELETE SERVICE");
        if (serviceRepository.existsById(id)) {
            log.info("START OF DELETE SERVICE BY ID");
            serviceRepository.delete(serviceRepository.getServiceById(id));
            return "deleted";
        }
        throw new AppException(HttpStatus.NOT_FOUND.value(),
                new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Not found service by id = " + id));
    }

    @Override
    public fiveman.hotelservice.entities.Service saveServices(fiveman.hotelservice.entities.Service service) {
        if (!serviceRepository.existsById(service.getId())) {
            service.setServiceCategory(serviceCategoryService.getServiceCategoryById(service.getServiceCategory().getId()));
            serviceRepository.save(service);
            return serviceRepository.getServiceById(service.getId());
        }
        throw new AppException(HttpStatus.ALREADY_REPORTED.value(),
                new CustomResponseObject(HttpStatus.NOT_FOUND.toString(), "Is exist service id =" + service.getId()));
    }

}
