package com.qlnhakhoa.appointment.controller;


import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.service.AppointmentService;
import com.qlnhakhoa.patient.service.PatientService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AppointmentController {



    @Autowired
    private AppointmentService appointmentService;



    @Autowired
    private PatientService patientService;




    // Danh sách lịch hẹn
    @GetMapping("/appointment")
    public String appointmentList(Model model){


        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments()
        );


        return "appointment/list";

    }





    // Mở form thêm lịch hẹn
    @GetMapping("/appointment/add")
    public String addAppointmentPage(Model model){


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





    // Lưu lịch hẹn
    @PostMapping("/appointment/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment,
            @RequestParam("patientId") Long patientId){



        appointment.setPatient(
                patientService.getPatientById(patientId)
        );


        appointmentService.saveAppointment(appointment);



        return "redirect:/appointment";

    }





    // Xóa lịch hẹn
    @GetMapping("/appointment/delete/{id}")
    public String deleteAppointment(
            @PathVariable Long id){



        appointmentService.deleteAppointment(id);



        return "redirect:/appointment";

    }




}