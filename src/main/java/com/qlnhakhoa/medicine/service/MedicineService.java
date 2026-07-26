package com.qlnhakhoa.medicine.service;

import com.qlnhakhoa.medicine.entity.Medicine;
import com.qlnhakhoa.medicine.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    // Lấy tất cả thuốc
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // Kiểm tra mã thuốc đã tồn tại
    public boolean existsByMedicineCode(String medicineCode) {
        return medicineRepository.existsByMedicineCode(medicineCode);
    }

    // Lưu / Cập nhật thông tin thuốc
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Lấy chi tiết thuốc theo ID
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    // Xóa thuốc
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    // Tìm kiếm thuốc theo từ khóa (Chuẩn hóa từ khóa tìm chính xác mã)
    public List<Medicine> searchMedicine(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllMedicines();
        }

        String trimmed = keyword.trim();

        // Chuẩn hóa exactCode
        String exactCode = trimmed;
        if (!exactCode.toUpperCase().startsWith("MED-")) {
            exactCode = "MED-" + trimmed;
        }

        return medicineRepository.searchCustom(exactCode, trimmed);
    }
}