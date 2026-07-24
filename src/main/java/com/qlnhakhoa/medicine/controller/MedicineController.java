package com.qlnhakhoa.medicine.controller;

import com.qlnhakhoa.medicine.entity.Medicine;
import com.qlnhakhoa.medicine.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // Danh sách thuốc
    @GetMapping("/medicine")
    public String medicineList(Model model) {
        model.addAttribute(
                "medicines",
                medicineService.getAllMedicines()
        );
        return "medicine/list";
    }

    // Mở trang thêm thuốc
    @GetMapping("/medicine/add")
    public String addMedicinePage(Model model) {
        model.addAttribute(
                "medicine",
                new Medicine()
        );
        return "medicine/add";
    }

    // Lưu thêm / cập nhật thuốc
    @PostMapping("/medicine/save")
    public String saveMedicine(@ModelAttribute Medicine medicine) {
        medicineService.saveMedicine(medicine);
        return "redirect:/medicine";
    }

    // Mở trang sửa thông tin thuốc
    @GetMapping("/medicine/edit/{id}")
    public String editMedicine(@PathVariable Long id, Model model) {
        Medicine medicine = medicineService.getMedicineById(id);
        model.addAttribute(
                "medicine",
                medicine
        );
        return "medicine/edit";
    }

    // Xóa thuốc
    @GetMapping("/medicine/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicine";
    }

    // Tìm kiếm thuốc
    @GetMapping("/medicine/search")
    public String searchMedicine(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "medicines",
                medicineService.searchMedicine(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "medicine/list";
    }
}