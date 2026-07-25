package com.qlnhakhoa.invoice.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping({"/invoice", "/invoice/list"})
    public String showInvoiceList(Model model) {
        List<Invoice> invoiceList = new ArrayList<>();
        try {
            if (invoiceRepository != null) {
                invoiceList = invoiceRepository.findAll();
            }
        } catch (Exception e) {
            System.err.println("Lỗi load danh sách hóa đơn: " + e.getMessage());
        }

        model.addAttribute("invoiceList", invoiceList);
        return "invoice/list"; 
    }

    @GetMapping("/invoice/checkout")
    public String checkoutWithoutId() {
        return "redirect:/invoice";
    }

    @GetMapping("/invoice/checkout/{id}")
    public String checkoutInvoice(@PathVariable("id") Long id, Model model) {
        Invoice invoice = null;
        try {
            if (invoiceRepository != null) {
                invoice = invoiceRepository.findById(id).orElse(null);
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm hóa đơn id " + id + ": " + e.getMessage());
        }

        model.addAttribute("invoice", invoice);
        return "invoice/checkout";
    }

    @PostMapping("/invoice/pay/{id}")
    public String processPayment(@PathVariable("id") Long id) {
        try {
            if (invoiceRepository != null) {
                Invoice invoice = invoiceRepository.findById(id).orElse(null);
                if (invoice != null) {
                    invoice.setPaymentStatus("PAID");
                    invoiceRepository.save(invoice);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật thanh toán: " + e.getMessage());
        }
        return "redirect:/invoice";
    }
}