package com.qlnhakhoa.patient.repository;


import com.qlnhakhoa.patient.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    List<Patient> findByPatientCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(
            String patientCode,
            String fullName,
            String phone
    );


}