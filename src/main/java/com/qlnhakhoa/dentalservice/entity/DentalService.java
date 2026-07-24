package com.qlnhakhoa.dentalservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dental_services")
public class DentalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String serviceCode;   // Mã dịch vụ (VD: DV01)

    @Column(nullable = false)
    private String serviceName;   // Tên dịch vụ (VD: Niềng răng, Tẩy trắng)

    private String unit;           // Đơn vị tính (VD: Răng, Liệu trình, Hàm)

    private Double price;          // Đơn giá

    @Column(length = 1000)
    private String description;    // Mô tả chi tiết dịch vụ

    public DentalService() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}