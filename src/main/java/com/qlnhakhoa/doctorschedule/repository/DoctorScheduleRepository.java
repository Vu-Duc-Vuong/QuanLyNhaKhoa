package com.qlnhakhoa.doctorschedule.repository;

import com.qlnhakhoa.doctorschedule.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    // Tìm kiếm lịch trực theo tên bác sĩ hoặc phòng khám
    List<DoctorSchedule> findByDoctorNameContainingIgnoreCaseOrClinicNameContainingIgnoreCase(
            String doctorName,
            String clinicName
    );
}