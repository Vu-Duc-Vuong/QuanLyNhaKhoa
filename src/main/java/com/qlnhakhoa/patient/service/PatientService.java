package com.qlnhakhoa.patient.service;


import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.patient.entity.Patient;
import com.qlnhakhoa.patient.repository.PatientRepository;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.treatment.repository.TreatmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PatientService {


    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TreatmentRepository treatmentRepository;



    // Lấy tất cả bệnh nhân
    public List<Patient> getAllPatients(){

        return patientRepository.findAll();

    }
    // Kiểm tra mã bệnh nhân đã tồn tại
public boolean existsByPatientCode(String patientCode) {
    return patientRepository.existsByPatientCode(patientCode);
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
    // true = xóa thành công
    // false = không cho xóa
    public boolean deletePatient(Long id){


        Patient patient = patientRepository
                .findById(id)
                .orElse(null);



        if(patient == null){

            return false;

        }




        // Kiểm tra bệnh nhân đã có lịch hẹn chưa
        if(patient.getAppointments() != null
                && !patient.getAppointments().isEmpty()){


            return false;

        }





        patientRepository.delete(patient);


        return true;

    }





    // Tìm kiếm bệnh nhân
    public List<Patient> searchPatient(String keyword){

        return patientRepository
                .findByPatientCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                );

    }


}