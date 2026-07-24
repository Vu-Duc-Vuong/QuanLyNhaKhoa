package com.qlnhakhoa.dentalservice.repository;

import com.qlnhakhoa.dentalservice.entity.DentalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentalServiceRepository extends JpaRepository<DentalService, Long> {

    // Tìm kiếm theo mã dịch vụ hoặc tên dịch vụ
    List<DentalService> findByServiceCodeContainingIgnoreCaseOrServiceNameContainingIgnoreCase(
            String serviceCode,
            String serviceName
    );
}