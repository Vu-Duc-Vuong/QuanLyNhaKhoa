package com.qlnhakhoa.invoice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private String patientName;

    @Column(name = "total_amount")
    private Double amount = 0.0;

    private String paymentStatus = "UNPAID";
    
    // Đổi sang LocalDateTime để lưu Ngày & Giờ
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(length = 1000)
    private String itemsSummary;

    // Constructor 1: Mặc định (bắt buộc cho JPA)
    public Invoice() {
        this.createdDate = LocalDateTime.now();
        this.paymentStatus = "UNPAID";
    }

    // Constructor 2: 3 tham số (Dùng khi tạo nhanh từ khám bệnh)
    public Invoice(Long patientId, String patientName, Double amount) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.amount = amount != null ? amount : 0.0;
        this.paymentStatus = "UNPAID";
        this.createdDate = LocalDateTime.now();
    }

    // Constructor 3: 4 tham số (Dùng trong InvoiceService)
    public Invoice(String patientName, Double amount, String paymentStatus, LocalDateTime createdDate) {
        this.patientName = patientName;
        this.amount = amount != null ? amount : 0.0;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "UNPAID";
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();
    }

    // Getters and Setters an toàn
    public Long getId() { return id != null ? id : 0L; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId != null ? patientId : 0L; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName != null ? patientName : "N/A"; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Double getAmount() { return amount != null ? amount : 0.0; }
    public void setAmount(Double amount) { this.amount = amount; }

    // Phương thức giả lập getter để tương thích Thymeleaf gọi totalAmount
    public Double getTotalAmount() { return getAmount(); }

    public String getPaymentStatus() { return paymentStatus != null ? paymentStatus : "UNPAID"; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getCreatedDate() { return createdDate != null ? createdDate : LocalDateTime.now(); }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getItemsSummary() { return itemsSummary != null ? itemsSummary : ""; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }
}