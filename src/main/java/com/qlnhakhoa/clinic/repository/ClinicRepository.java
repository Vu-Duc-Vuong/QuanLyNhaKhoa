package com.qlnhakhoa.clinic.repository;

import com.qlnhakhoa.clinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    // Tìm kiếm theo mã phòng hoặc tên phòng
    List<Clinic> findByClinicCodeContainingIgnoreCaseOrClinicNameContainingIgnoreCase(
            String clinicCode,
            String clinicName
    );
}