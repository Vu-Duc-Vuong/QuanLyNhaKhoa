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






    // Lưu thêm + sửa lịch hẹn
    @PostMapping("/appointment/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment,
            @RequestParam("patientId") Long patientId,
            Model model){



        // kiểm tra trùng lịch
        boolean exists =
                appointmentService.checkDuplicate(
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime()
                );



        if(exists){


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
                    "Lịch hẹn này đã tồn tại!"
            );


            return "appointment/add";

        }




        appointment.setPatient(
                patientService.getPatientById(patientId)
        );



        appointmentService.saveAppointment(appointment);



        return "redirect:/appointment";

    }








    // Mở form sửa lịch hẹn
    @GetMapping("/appointment/edit/{id}")
    public String editAppointment(
            @PathVariable Long id,
            Model model){



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
            @PathVariable String status){



        appointmentService.updateStatus(
                id,
                status
        );


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