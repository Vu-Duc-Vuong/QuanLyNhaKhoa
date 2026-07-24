package com.qlnhakhoa.dentalservice.service;

import com.qlnhakhoa.dentalservice.entity.DentalService;
import com.qlnhakhoa.dentalservice.repository.DentalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentalServiceService {

    @Autowired
    private DentalServiceRepository serviceRepository;

    // Lấy tất cả dịch vụ
    public List<DentalService> getAllServices() {
        return serviceRepository.findAll();
    }

    // Lưu / Cập nhật dịch vụ
    public DentalService saveService(DentalService service) {
        return serviceRepository.save(service);
    }

    // Lấy dịch vụ theo ID
    public DentalService getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    // Xóa dịch vụ
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    // Tìm kiếm dịch vụ
    public List<DentalService> searchService(String keyword) {
        return serviceRepository
                .findByServiceCodeContainingIgnoreCaseOrServiceNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}