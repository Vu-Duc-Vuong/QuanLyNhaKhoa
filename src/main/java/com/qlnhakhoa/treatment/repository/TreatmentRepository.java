package com.qlnhakhoa.treatment.repository;


import com.qlnhakhoa.treatment.entity.Treatment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {


    // Lấy hồ sơ khám theo lịch hẹn
    Optional<Treatment> findByAppointmentId(Long appointmentId);



    // Lấy danh sách theo trạng thái
    List<Treatment> findByStatus(String status);



    // Tìm kiếm theo mã hồ sơ khám hoặc tên bệnh nhân
    List<Treatment> findByTreatmentCodeContainingIgnoreCaseOrAppointment_Patient_FullNameContainingIgnoreCase(
            String treatmentCode,
            String fullName
    );


}
