package com.qlnhakhoa.clinic.controller;

import com.qlnhakhoa.clinic.entity.Clinic;
import com.qlnhakhoa.clinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    // Danh sách phòng khám
    @GetMapping("/clinic")
    public String clinicList(Model model) {
        model.addAttribute(
                "clinics",
                clinicService.getAllClinics()
        );
        return "clinic/list";
    }

    // Mở trang thêm phòng khám
    @GetMapping("/clinic/add")
    public String addClinicPage(Model model) {
        model.addAttribute(
                "clinic",
                new Clinic()
        );
        return "clinic/add";
    }

    // Lưu thêm / cập nhật phòng khám
    @PostMapping("/clinic/save")
    public String saveClinic(@ModelAttribute Clinic clinic) {
        clinicService.saveClinic(clinic);
        return "redirect:/clinic";
    }

    // Mở trang sửa phòng khám
    @GetMapping("/clinic/edit/{id}")
    public String editClinic(@PathVariable Long id, Model model) {
        Clinic clinic = clinicService.getClinicById(id);
        model.addAttribute(
                "clinic",
                clinic
        );
        return "clinic/edit";
    }

    // Xóa phòng khám
    @GetMapping("/clinic/delete/{id}")
    public String deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return "redirect:/clinic";
    }

    // Tìm kiếm phòng khám
    @GetMapping("/clinic/search")
    public String searchClinic(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "clinics",
                clinicService.searchClinic(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "clinic/list";
    }
}