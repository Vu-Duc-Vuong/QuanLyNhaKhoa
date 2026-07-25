package com.qlnhakhoa.report.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public String showReport(Model model) {
        List<Invoice> invoiceList = null;
        try {
            invoiceList = invoiceService.getAllInvoices();
        } catch (Exception e) {
            invoiceList = null;
        }

        double totalRevenue = 0;
        if (invoiceList != null && !invoiceList.isEmpty()) {
            for (Invoice inv : invoiceList) {
                if (inv.getTotalAmount() != null) {
                    totalRevenue += inv.getTotalAmount();
                }
            }
        }

        model.addAttribute("invoiceList", invoiceList);
        model.addAttribute("totalRevenue", totalRevenue);

        return "report/dashboard";
    }
}