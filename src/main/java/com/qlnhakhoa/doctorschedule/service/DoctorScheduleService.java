package com.qlnhakhoa.doctorschedule.service;

import com.qlnhakhoa.doctorschedule.entity.DoctorSchedule;
import com.qlnhakhoa.doctorschedule.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    // Lấy tất cả lịch trực
    public List<DoctorSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Lưu / Cập nhật lịch trực
    public DoctorSchedule saveSchedule(DoctorSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // Lấy lịch trực theo ID
    public DoctorSchedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    // Xóa lịch trực
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    // Tìm kiếm lịch trực
    public List<DoctorSchedule> searchSchedule(String keyword) {
        return scheduleRepository
                .findByDoctorNameContainingIgnoreCaseOrClinicNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}