package com.qlnhakhoa.dentalservice.service;

import com.qlnhakhoa.dentalservice.entity.DentalService;
import com.qlnhakhoa.dentalservice.repository.DentalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentalServiceService {

    @Autowired
    private DentalServiceRepository repository;

    public List<DentalService> getAllServices() {
        return repository.findAll();
    }

    public DentalService getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với ID: " + id));
    }

    public DentalService create(DentalService dentalService) {
        return repository.save(dentalService);
    }

    public DentalService update(Long id, DentalService details) {
        DentalService service = getById(id);
        service.setName(details.getName());
        service.setPrice(details.getPrice());
        service.setUnit(details.getUnit());
        service.setDescription(details.getDescription());
        service.setActive(details.getActive());
        return repository.save(service);
    }

    public void delete(Long id) {
        DentalService service = getById(id);
        service.setActive(false); // Xóa mềm
        repository.save(service);
    }
}