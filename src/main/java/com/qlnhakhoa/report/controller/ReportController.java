package com.qlnhakhoa.report.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {

    @Autowired(required = false)
    private InvoiceRepository invoiceRepository;

    @GetMapping({"/report", "/report/revenue", "/report/dashboard"})
    public String showReport(Model model) {
        List<Invoice> invoiceList = new ArrayList<>();
        double totalRevenue = 0.0;
        double todayRevenue = 0.0;
        long totalInvoices = 0;
        long paidInvoices = 0;

        try {
            if (invoiceRepository != null) {
                invoiceList = invoiceRepository.findAll();
                if (invoiceList == null) {
                    invoiceList = new ArrayList<>();
                }
                
                totalInvoices = invoiceList.size();
                LocalDate today = LocalDate.now();

                for (Invoice inv : invoiceList) {
                    if (inv == null) continue;

                    // Lấy số tiền an toàn, chống null
                    double amount = inv.getAmount() != null ? inv.getAmount() : 0.0;
                    totalRevenue += amount;

                    // Kiểm tra trạng thái an toàn
                    if ("PAID".equalsIgnoreCase(inv.getPaymentStatus())) {
                        paidInvoices++;
                    }

                    // Kiểm tra ngày tạo an toàn (SỬA LỖI 500 Ở ĐÂY)
                    if (inv.getCreatedDate() != null && inv.getCreatedDate().isEqual(today)) {
                        todayRevenue += amount;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi xử lý báo cáo: " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("invoiceList", invoiceList);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("todayRevenue", todayRevenue);
        model.addAttribute("totalInvoices", totalInvoices);
        model.addAttribute("paidInvoices", paidInvoices);

        return "report/dashboard";
    }
}