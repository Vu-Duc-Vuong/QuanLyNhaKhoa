package com.qlnhakhoa.doctorschedule.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "doctor_schedules")
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String doctorName;  // Tên Bác sĩ (VD: BS. Nguyễn Văn A)

    private String clinicName;  // Phòng khám làm việc (VD: Phòng Khám 01)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate; // Ngày làm việc

    private String shift;       // Ca làm việc (Ca sáng, Ca chiều, Ca tối, Cả ngày)

    private String status;      // Trạng thái (Đã xếp lịch, Đang trực, Đã hoàn thành, Nghỉ)

    @Column(length = 500)
    private String note;        // Ghi chú

    public DoctorSchedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}