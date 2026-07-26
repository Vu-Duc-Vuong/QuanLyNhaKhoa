package com.qlnhakhoa.patient.controller;

import com.qlnhakhoa.patient.entity.Patient;
import com.qlnhakhoa.patient.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Danh sách bệnh nhân
    @GetMapping("/patient")
    public String patientList(Model model) {

        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );

        return "patient/list";
    }

    // Mở form thêm bệnh nhân
    @GetMapping("/patient/add")
    public String addPatientPage(Model model) {

        model.addAttribute(
                "patient",
                new Patient()
        );

        return "patient/add";
    }

    // Lưu bệnh nhân
    @PostMapping("/patient/save")
    public String savePatient(
            @ModelAttribute Patient patient,
            Model model) {

        // Kiểm tra trùng mã bệnh nhân (chỉ khi thêm mới)
        if (patient.getId() == null
                && patientService.existsByPatientCode(patient.getPatientCode())) {

            model.addAttribute("patient", patient);
            model.addAttribute("error", "Mã bệnh nhân đã tồn tại!");

            return "patient/add";
        }

        patientService.savePatient(patient);

        return "redirect:/patient";
    }

    // Xóa bệnh nhân
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        boolean deleted = patientService.deletePatient(id);

        if (deleted) {

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Xóa bệnh nhân thành công."
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không thể xóa bệnh nhân vì đã có lịch hẹn hoặc hồ sơ khám."
            );
        }

        return "redirect:/patient";
    }

    // Mở form sửa bệnh nhân
    @GetMapping("/patient/edit/{id}")
    public String editPatient(
            @PathVariable Long id,
            Model model) {

        Patient patient = patientService.getPatientById(id);

        model.addAttribute(
                "patient",
                patient
        );

        return "patient/edit";
    }

    // Tìm kiếm bệnh nhân
    @GetMapping("/patient/search")
    public String searchPatient(
            @RequestParam("keyword") String keyword,
            Model model) {

        model.addAttribute(
                "patients",
                patientService.searchPatient(keyword)
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        return "patient/list";
    }
}