package com.qlnhakhoa.dentalchair.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dental_chairs")
public class DentalChair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String chairCode;   // Mã ghế (VD: G01, G02)

    @Column(nullable = false)
    private String chairName;   // Tên ghế / Số ghế (VD: Ghế số 01)

    private String clinicName;  // Thuộc phòng khám nào (VD: Phòng Khám 1)

    private String status;      // Trạng thái (Trống, Đang sử dụng, Đang bảo trì, Hỏng)

    @Column(length = 500)
    private String description; // Ghi chú / Mô tả thiết bị

    public DentalChair() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChairCode() {
        return chairCode;
    }

    public void setChairCode(String chairCode) {
        this.chairCode = chairCode;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}