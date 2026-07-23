package com.qlnhakhoa.medicine.service;

import com.qlnhakhoa.medicine.entity.Medicine;
import com.qlnhakhoa.medicine.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository repository;

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    public Medicine getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại thuốc với ID: " + id));
    }

    public Medicine create(Medicine medicine) {
        return repository.save(medicine);
    }

    public Medicine update(Long id, Medicine details) {
        Medicine medicine = getById(id);
        medicine.setName(details.getName());
        medicine.setUnit(details.getUnit());
        medicine.setStockQuantity(details.getStockQuantity());
        medicine.setPrice(details.getPrice());
        medicine.setUsageInstructions(details.getUsageInstructions());
        return repository.save(medicine);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}