package com.qlnhakhoa.dashboard.controller;


import com.qlnhakhoa.patient.repository.PatientRepository;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public String dashboard(
            @RequestParam(required = false) String date,
            Model model
    ){


        // Ngày đang xem
        LocalDate selectedDate;


        if(date != null && !date.isEmpty()){

            selectedDate = LocalDate.parse(date);

        }else{

            selectedDate = LocalDate.now();

        }





        // Tổng bệnh nhân
        long totalPatients =
                patientRepository.count();






        // Danh sách lịch theo ngày
        List<Appointment> appointments =
                appointmentRepository
                        .findByAppointmentDateOrderByAppointmentTimeAsc(
                                selectedDate
                        );






        // Danh sách bệnh nhân đang chờ khám trong ngày
        List<Appointment> waitingList =
                appointmentRepository
                        .findByAppointmentDateAndStatusOrderByAppointmentTimeAsc(
                                selectedDate,
                                "Chờ khám"
                        );







        // Tổng doanh thu
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
                appointments.size()
        );



        model.addAttribute(
                "waitingAppointments",
                waitingList.size()
        );



        model.addAttribute(
                "appointments",
                appointments
        );



        model.addAttribute(
                "waitingList",
                waitingList
        );



        model.addAttribute(
                "selectedDate",
                selectedDate
        );



        model.addAttribute(
                "revenue",
                revenue
        );





        return "layout/dashboard";

    }


}