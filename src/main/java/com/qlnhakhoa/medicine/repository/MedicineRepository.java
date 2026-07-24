package com.qlnhakhoa.medicine.repository;

import com.qlnhakhoa.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Tìm kiếm theo mã thuốc hoặc tên thuốc (không phân biệt hoa thường)
    List<Medicine> findByMedicineCodeContainingIgnoreCaseOrMedicineNameContainingIgnoreCase(
            String medicineCode,
            String medicineName
    );
}