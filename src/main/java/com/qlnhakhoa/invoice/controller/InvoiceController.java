package com.qlnhakhoa.invoice.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.service.InvoiceService;
import com.qlnhakhoa.patient.entity.Patient;
import com.qlnhakhoa.patient.repository.PatientRepository;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.treatment.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired(required = false)
    private TreatmentRepository treatmentRepository;

    @GetMapping("/checkout/{id}")
    public String showCheckout(@PathVariable("id") Long id, Model model) {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setCreatedDate(LocalDate.now());

        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            invoice.setPatientName(patient.getFullName());
        } else {
            invoice.setPatientName("Bệnh nhân #" + id);
        }

        Double realAmount = null;

        if (treatmentRepository != null) {
            Treatment treatment = treatmentRepository.findById(id).orElse(null);
            if (treatment != null && treatment.getTotalAmount() != null && treatment.getTotalAmount() > 0) {
                realAmount = treatment.getTotalAmount();
            }
        }

        if (realAmount == null) {
            realAmount = 200000.0 * id; 
        }

        invoice.setTotalAmount(realAmount);

        model.addAttribute("invoice", invoice);
        return "invoice/checkout";
    }

    @GetMapping("/checkout")
    public String defaultCheckout() {
        return "redirect:/invoice/checkout/1";
    }

    @PostMapping("/pay")
    public String processPayment(@RequestParam("patientName") String patientName, 
                                 @RequestParam("totalAmount") Double totalAmount) {
        invoiceService.createInvoice(patientName, totalAmount);
        return "redirect:/report"; 
    }
}