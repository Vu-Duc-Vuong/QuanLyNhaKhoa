package com.qlnhakhoa.clinic.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clinics")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String clinicCode;   // Mã phòng (VD: PK01)

    @Column(nullable = false)
    private String clinicName;   // Tên phòng khám (VD: Phòng Khám Tổng Quát 1)

    private String location;     // Vị trí / Tầng (VD: Tầng 2 - Phòng 201)

    private String status;       // Trạng thái (Hoạt động, Đang bảo trì, Tạm đóng)

    public Clinic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicCode() {
        return clinicCode;
    }

    public void setClinicCode(String clinicCode) {
        this.clinicCode = clinicCode;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}