package com.qlnhakhoa.treatment.entity;


import jakarta.persistence.*;


// Một dòng dịch vụ nha khoa được bác sĩ chỉ định trong hồ sơ khám
// (vd: nhổ răng, trám răng, chụp X-quang, cạo vôi răng...)
@Entity
@Table(name = "service_order_items")
public class ServiceOrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;



    // Tên dịch vụ
    @Column(nullable = false)
    private String serviceName;



    // Đơn giá
    private Double price;



    // Số lượng (mặc định 1)
    private Integer quantity = 1;



    // Ghi chú
    private String note;



    public ServiceOrderItem() {
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    // Thành tiền của dòng dịch vụ này
    public Double getLineTotal() {

        if (price == null || quantity == null) {
            return 0.0;
        }

        return price * quantity;

    }

}
