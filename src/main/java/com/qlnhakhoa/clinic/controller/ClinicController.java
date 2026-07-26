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

    // Danh sách nha khoa
    @GetMapping("/clinic")
    public String clinicList(Model model) {

        model.addAttribute(
                "clinics",
                clinicService.getAllClinics()
        );

        return "clinic/list";
    }

    // Mở form thêm nha khoa
    @GetMapping("/clinic/add")
    public String addClinicPage(Model model) {

        model.addAttribute(
                "clinic",
                new Clinic()
        );

        return "clinic/add";
    }

    // Lưu nha khoa
    @PostMapping("/clinic/save")
    public String saveClinic(@ModelAttribute Clinic clinic) {

        if (clinic.getStatus() == null || clinic.getStatus().isBlank()) {
            clinic.setStatus("ACTIVE");
        }

        clinicService.saveClinic(clinic);

        return "redirect:/clinic";
    }

    // Mở form sửa nha khoa
    @GetMapping("/clinic/edit/{id}")
    public String editClinic(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "clinic",
                clinicService.getClinicById(id)
        );

        return "clinic/edit";
    }

    // Xóa nha khoa
    @GetMapping("/clinic/delete/{id}")
    public String deleteClinic(@PathVariable Long id) {

        clinicService.deleteClinic(id);

        return "redirect:/clinic";
    }

    // Tìm kiếm nha khoa
    @GetMapping("/clinic/search")
    public String searchClinic(
            @RequestParam("keyword") String keyword,
            Model model) {

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