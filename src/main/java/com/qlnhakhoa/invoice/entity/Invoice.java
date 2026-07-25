package com.qlnhakhoa.invoice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

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
    private LocalDate createdDate = LocalDate.now();

    @Column(length = 1000)
    private String itemsSummary;

    // Constructor 1: Mặc định (bắt buộc cho JPA)
    public Invoice() {
        this.createdDate = LocalDate.now();
        this.paymentStatus = "UNPAID";
    }

    // Constructor 2: 3 tham số (Dùng khi tạo nhanh từ khám bệnh)
    public Invoice(Long patientId, String patientName, Double amount) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.amount = amount != null ? amount : 0.0;
        this.paymentStatus = "UNPAID";
        this.createdDate = LocalDate.now();
    }

    // Constructor 3: 4 tham số (Dùng trong InvoiceService)
    public Invoice(String patientName, Double amount, String paymentStatus, LocalDate createdDate) {
        this.patientName = patientName;
        this.amount = amount != null ? amount : 0.0;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "UNPAID";
        this.createdDate = createdDate != null ? createdDate : LocalDate.now();
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

    public LocalDate getCreatedDate() { return createdDate != null ? createdDate : LocalDate.now(); }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public String getItemsSummary() { return itemsSummary != null ? itemsSummary : ""; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }
}