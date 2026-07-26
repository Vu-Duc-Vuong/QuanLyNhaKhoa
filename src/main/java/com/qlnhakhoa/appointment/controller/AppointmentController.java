package com.qlnhakhoa.appointment.controller;

import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.service.AppointmentService;
import com.qlnhakhoa.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    // Danh sách lịch hẹn
    @GetMapping("/appointment")
    public String appointmentList(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments()
        );

        return "appointment/list";
    }

    // Mở form thêm lịch hẹn
    @GetMapping("/appointment/add")
    public String addAppointmentPage(Model model) {

        model.addAttribute(
                "appointment",
                new Appointment()
        );

        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );

        return "appointment/add";
    }

    // Lưu thêm + sửa lịch hẹn
    @PostMapping("/appointment/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment,
            @RequestParam("patientId") Long patientId,
            Model model) {

        // Kiểm tra trùng lịch
        boolean exists = appointmentService.checkDuplicate(
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );

        if (exists) {

            model.addAttribute("appointment", appointment);
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("error", "Lịch hẹn này đã tồn tại!");

            return "appointment/add";
        }

        appointment.setPatient(
                patientService.getPatientById(patientId)
        );

        try {

            appointmentService.saveAppointment(appointment);

        } catch (RuntimeException e) {

            model.addAttribute(
                    "appointment",
                    appointment
            );

            model.addAttribute(
                    "patients",
                    patientService.getAllPatients()
            );

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            return "appointment/add";
        }

        return "redirect:/appointment";
    }

    // Mở form sửa lịch hẹn
    @GetMapping("/appointment/edit/{id}")
    public String editAppointment(
            @PathVariable Long id,
            Model model) {

        Appointment appointment =
                appointmentService.getAppointmentById(id);

        model.addAttribute(
                "appointment",
                appointment
        );

        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );

        return "appointment/edit";
    }

    // Đổi trạng thái lịch hẹn
    @GetMapping("/appointment/status/{id}/{status}")
    public String updateStatus(
            @PathVariable Long id,
            @PathVariable String status) {

        appointmentService.updateStatus(
                id,
                status
        );

        return "redirect:/appointment";
    }

    // Xóa lịch hẹn
    @GetMapping("/appointment/delete/{id}")
    public String deleteAppointment(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            appointmentService.deleteAppointment(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Xóa lịch hẹn thành công."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không thể xóa lịch hẹn vì đã có hồ sơ điều trị."
            );
        }

        return "redirect:/appointment";
    }

    // Tìm kiếm lịch hẹn
    @GetMapping("/appointment/search")
    public String searchAppointment(
            @RequestParam("keyword") String keyword,
            Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.search(keyword)
        );

        return "appointment/list";
    }
}