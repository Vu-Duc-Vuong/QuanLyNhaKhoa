package com.qlnhakhoa.dashboard.controller;


import com.qlnhakhoa.patient.repository.PatientRepository;
import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDate;
import java.util.List;



@Controller
public class DashboardController {


    @Autowired
    private PatientRepository patientRepository;



    @Autowired
    private AppointmentRepository appointmentRepository;



    @Autowired
    private InvoiceRepository invoiceRepository;




    @GetMapping("/home")
    public String dashboard(Model model){


        LocalDate today = LocalDate.now();



        // Tổng bệnh nhân
        long totalPatients =
                patientRepository.count();




        // Lịch hôm nay
        List<Appointment> todayAppointmentList =
                appointmentRepository
                        .findByAppointmentDateOrderByAppointmentTimeAsc(today);



        // Đang chờ khám hôm nay
        List<Appointment> waitingList =
                appointmentRepository
                        .findByAppointmentDateAndStatusOrderByAppointmentTimeAsc(
                                today,
                                "Chờ khám"
                        );




        // Doanh thu
        Double revenue =
                invoiceRepository.sumTotalAmount();



        if(revenue == null){
            revenue = 0.0;
        }





        model.addAttribute(
                "totalPatients",
                totalPatients
        );


        model.addAttribute(
                "todayAppointments",
                todayAppointmentList.size()
        );


        model.addAttribute(
                "waitingAppointments",
                waitingList.size()
        );


        model.addAttribute(
                "today",
                today
        );


        model.addAttribute(
                "appointmentList",
                todayAppointmentList
        );


        model.addAttribute(
                "revenue",
                revenue
        );



        return "layout/dashboard";

    }

}