package com.qlnhakhoa.treatment.entity;


import jakarta.persistence.*;


// Một dòng thuốc trong đơn kê thuốc của hồ sơ khám
// Lưu ý: hiện tại nhập tên thuốc trực tiếp (chưa liên kết bảng danh mục thuốc
// vì module "medicine" do thành viên khác phụ trách). Sau này nếu module
// medicine hoàn thiện, có thể thêm @ManyToOne Medicine để chọn từ danh mục.
@Entity
@Table(name = "prescription_items")
public class PrescriptionItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;



    // Tên thuốc
    @Column(nullable = false)
    private String medicineName;



    // Liều dùng, vd: "2 viên/lần x 2 lần/ngày sau ăn"
    private String dosage;



    // Số lượng
    private Integer quantity;



    // Đơn vị: viên, vỉ, chai, tuýp...
    private String unit;



    // Ghi chú thêm
    private String note;



    public PrescriptionItem() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
