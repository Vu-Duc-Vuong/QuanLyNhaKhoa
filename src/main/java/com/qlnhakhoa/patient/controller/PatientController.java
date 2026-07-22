package com.qlnhakhoa.patient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    @GetMapping("/patient")
    public String patientList() {
        return "patient/list";
    }
}