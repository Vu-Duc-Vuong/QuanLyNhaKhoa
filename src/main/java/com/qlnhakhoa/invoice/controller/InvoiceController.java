package com.qlnhakhoa.invoice.controller;

import com.qlnhakhoa.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Hiển thị trang thanh toán
    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        model.addAttribute("patientName", "Lê Thị B");
        model.addAttribute("totalAmount", 320000);
        return "invoice/checkout";
    }

    // Xác nhận thanh toán & Lưu DB
    @PostMapping("/pay")
    public String processPayment() {
        invoiceService.createInvoice("Lê Thị B", 320000.0);
        return "redirect:/report/dashboard";
    }
}