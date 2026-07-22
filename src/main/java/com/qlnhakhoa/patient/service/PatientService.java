package com.qlnhakhoa.patient.service;


import com.qlnhakhoa.patient.entity.Patient;
import com.qlnhakhoa.patient.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService {


    @Autowired
    private PatientRepository patientRepository;



    // Lấy tất cả bệnh nhân
    public List<Patient> getAllPatients(){

        return patientRepository.findAll();

    }



    // Lưu / cập nhật bệnh nhân
    public Patient savePatient(Patient patient){

        return patientRepository.save(patient);

    }



    // Lấy bệnh nhân theo ID để sửa
    public Patient getPatientById(Long id){

        return patientRepository
                .findById(id)
                .orElse(null);

    }



    // Xóa bệnh nhân
    public void deletePatient(Long id){

        patientRepository.deleteById(id);

    }


}