package com.qlnhakhoa.dashboard.controller;


import com.qlnhakhoa.patient.repository.PatientRepository;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.time.LocalDate;



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



        // Tổng bệnh nhân
        long totalPatients =
                patientRepository.count();





       long todayAppointments =
        appointmentRepository
                .findByAppointmentDateOrderByAppointmentTimeAsc(
                        LocalDate.now()
                )
                .size();




long waitingAppointments =
        appointmentRepository
                .findByAppointmentDateAndStatusOrderByAppointmentTimeAsc(
                        LocalDate.now(),
                        "Chờ khám"
                )
                .size();





        // Tổng doanh thu
        Double revenue =
                invoiceRepository
                        .sumTotalAmount();



        if(revenue == null){

            revenue = 0.0;

        }







        model.addAttribute(
                "totalPatients",
                totalPatients
        );



        model.addAttribute(
                "todayAppointments",
                todayAppointments
        );



        model.addAttribute(
                "waitingAppointments",
                waitingAppointments
        );



        model.addAttribute(
                "revenue",
                revenue
        );





       return "layout/dashboard";

    }


}