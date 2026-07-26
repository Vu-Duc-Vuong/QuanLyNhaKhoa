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



    // Lấy danh sách tất cả nha khoa
    public List<Clinic> getAllClinics() {

        return clinicRepository.findAll();

    }



    // Thêm / cập nhật nha khoa
    public Clinic saveClinic(Clinic clinic) {

        return clinicRepository.save(clinic);

    }



    // Lấy nha khoa theo id
    public Clinic getClinicById(Long id) {

        return clinicRepository.findById(id)
                .orElse(null);

    }



    // Xóa nha khoa
    public void deleteClinic(Long id) {

        clinicRepository.deleteById(id);

    }



    // Tìm kiếm nha khoa theo mã hoặc tên
    public List<Clinic> searchClinic(String keyword) {


        return clinicRepository
                .findByClinicCodeContainingIgnoreCaseOrClinicNameContainingIgnoreCase(
                        keyword,
                        keyword
                );

    }

}