package com.qlnhakhoa.medicine.repository;

import com.qlnhakhoa.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Tìm CHÍNH XÁC mã thuốc HOẶC tìm GẦN ĐÚNG theo tên thuốc
    @Query("SELECT m FROM Medicine m WHERE m.medicineCode = :exactCode OR LOWER(m.medicineName) LIKE LOWER(CONCAT('%', :nameKeyword, '%'))")
    List<Medicine> searchCustom(@Param("exactCode") String exactCode, @Param("nameKeyword") String nameKeyword);
}