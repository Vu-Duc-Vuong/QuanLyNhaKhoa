package com.qlnhakhoa.clinic.service;

import com.qlnhakhoa.clinic.entity.Clinic;
import com.qlnhakhoa.clinic.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    // Lấy tất cả phòng khám
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    // Lưu / Cập nhật phòng khám
    public Clinic saveClinic(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    // Lấy phòng khám theo ID
    public Clinic getClinicById(Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    // Xóa phòng khám
    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }

    // Tìm kiếm phòng khám
    public List<Clinic> searchClinic(String keyword) {
        return clinicRepository
                .findByClinicCodeContainingIgnoreCaseOrClinicNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}