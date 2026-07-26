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





    // Form thêm
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






    // Lưu lịch hẹn
    @PostMapping("/appointment/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment,
            @RequestParam("patientId") Long patientId,
            Model model) {



        /*
         * Chuẩn hóa mã lịch
         * 5 -> LH-005
         */
        try {

            String code =
                    appointment.getAppointmentCode()
                            .replace("LH-", "")
                            .trim();


            int number =
                    Integer.parseInt(code);


            appointment.setAppointmentCode(
                    String.format(
                            "LH-%03d",
                            number
                    )
            );


        } catch(Exception e) {


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
                    "Mã lịch hẹn chỉ được nhập số!"
            );


            return "appointment/add";
        }






        /*
         * Kiểm tra trùng mã lịch
         */

        if(
            appointmentService
                    .existsByAppointmentCode(
                            appointment.getAppointmentCode()
                    )
        ) {


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
                    "Mã lịch hẹn đã tồn tại!"
            );


            return "appointment/add";
        }








        /*
         * Kiểm tra trùng ngày giờ
         */

        if(
            appointmentService.checkDuplicate(
                    appointment.getAppointmentDate(),
                    appointment.getAppointmentTime()
            )
        ) {


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





        try {


            appointmentService.saveAppointment(
                    appointment
            );


        } catch(RuntimeException e) {


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









    // sửa lịch

    @GetMapping("/appointment/edit/{id}")
    public String editAppointment(
            @PathVariable Long id,
            Model model) {


        model.addAttribute(
                "appointment",
                appointmentService.getAppointmentById(id)
        );


        model.addAttribute(
                "patients",
                patientService.getAllPatients()
        );


        return "appointment/edit";
    }







    // đổi trạng thái

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








    // xóa

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


        } catch(Exception e) {


            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không thể xóa lịch hẹn vì đã có hồ sơ điều trị."
            );

        }


        return "redirect:/appointment";

    }







    // tìm kiếm

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