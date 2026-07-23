package com.qlnhakhoa.treatment.entity;


import com.qlnhakhoa.appointment.entity.Appointment;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "treatments")
public class Treatment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // Mã hồ sơ khám
    @Column(unique = true)
    private String treatmentCode;



    // Liên kết tới lịch hẹn (mỗi lịch hẹn có đúng 1 hồ sơ khám)
    @OneToOne
    @JoinColumn(name = "appointment_id", unique = true)
    private Appointment appointment;



    // Triệu chứng / lý do khám do bác sĩ ghi nhận thêm
    @Column(length = 1000)
    private String symptom;



    // Chẩn đoán
    @Column(length = 1000)
    private String diagnosis;



    // Ghi chú của bác sĩ
    @Column(length = 1000)
    private String note;



    // Trạng thái hồ sơ khám: "Đang khám" / "Hoàn thành"
    private String status;



    // Tổng tiền (tính từ các dịch vụ chỉ định)
    private Double totalAmount = 0.0;



    private LocalDate createdDate;

    private LocalDate completedDate;



    // Danh sách thuốc đã kê
    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> prescriptionItems = new ArrayList<>();



    // Danh sách dịch vụ đã chỉ định
    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOrderItem> serviceOrderItems = new ArrayList<>();



    public Treatment() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreatmentCode() {
        return treatmentCode;
    }

    public void setTreatmentCode(String treatmentCode) {
        this.treatmentCode = treatmentCode;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public List<PrescriptionItem> getPrescriptionItems() {
        return prescriptionItems;
    }

    public void setPrescriptionItems(List<PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    public List<ServiceOrderItem> getServiceOrderItems() {
        return serviceOrderItems;
    }

    public void setServiceOrderItems(List<ServiceOrderItem> serviceOrderItems) {
        this.serviceOrderItems = serviceOrderItems;
    }

}
