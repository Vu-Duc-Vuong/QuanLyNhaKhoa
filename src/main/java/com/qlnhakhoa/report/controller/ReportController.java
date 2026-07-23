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

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        
        // Tính tổng doanh thu từ danh sách hóa đơn
        double totalRevenue = invoices.stream()
                .mapToDouble(invoice -> invoice.getTotalAmount() != null ? invoice.getTotalAmount() : 0.0)
                .sum();

        model.addAttribute("invoices", invoices);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalCount", invoices.size());

        return "report/dashboard";
    }
}