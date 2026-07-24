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

    // Tìm kiếm thuốc theo từ khóa
    public List<Medicine> searchMedicine(String keyword) {
        return medicineRepository
                .findByMedicineCodeContainingIgnoreCaseOrMedicineNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}