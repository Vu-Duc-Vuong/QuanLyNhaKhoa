package com.qlnhakhoa.report.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportController {

    @Autowired(required = false)
    private InvoiceRepository invoiceRepository;

    @GetMapping({"/report", "/report/revenue", "/report/dashboard"})
    public String showReport(
            @RequestParam(value = "month", required = false) Integer selectedMonth,
            @RequestParam(value = "year", required = false) Integer selectedYear,
            Model model) {

        List<Invoice> invoiceList = new ArrayList<>();
        List<Invoice> monthInvoiceList = new ArrayList<>();
        
        double totalRevenue = 0.0;
        double todayRevenue = 0.0;
        long totalInvoices = 0;
        long todayInvoicesCount = 0;

        LocalDate today = LocalDate.now();
        int currentMonth = (selectedMonth != null) ? selectedMonth : today.getMonthValue();
        int currentYear = (selectedYear != null) ? selectedYear : today.getYear();
        YearMonth selectedYearMonth = YearMonth.of(currentYear, currentMonth);

        try {
            if (invoiceRepository != null) {
                invoiceList = invoiceRepository.findAll();
                if (invoiceList == null) {
                    invoiceList = new ArrayList<>();
                }

                for (Invoice inv : invoiceList) {
                    if (inv == null || inv.getCreatedDate() == null) continue;

                    LocalDate invDate = inv.getCreatedDate().toLocalDate();

                    // 1. Lọc và Tính toán theo Tháng/Năm được chọn
                    if (YearMonth.from(inv.getCreatedDate()).equals(selectedYearMonth)) {
                        monthInvoiceList.add(inv);
                        if ("PAID".equalsIgnoreCase(inv.getPaymentStatus())) {
                            totalRevenue += (inv.getAmount() != null ? inv.getAmount() : 0.0);
                        }

                        // 2. Chỉ tính "Hôm Nay" nếu ngày hôm nay nằm trong tháng/năm đang chọn
                        if (invDate.equals(today)) {
                            todayInvoicesCount++;
                            if ("PAID".equalsIgnoreCase(inv.getPaymentStatus())) {
                                todayRevenue += (inv.getAmount() != null ? inv.getAmount() : 0.0);
                            }
                        }
                    }
                }
                totalInvoices = monthInvoiceList.size();
            }
        } catch (Exception e) {
            System.err.println("Lỗi xử lý báo cáo: " + e.getMessage());
            e.printStackTrace();
        }

        model.addAttribute("invoiceList", monthInvoiceList);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("todayRevenue", todayRevenue);
        model.addAttribute("totalInvoices", totalInvoices);
        model.addAttribute("todayInvoicesCount", todayInvoicesCount);
        model.addAttribute("selectedMonth", currentMonth);
        model.addAttribute("selectedYear", currentYear);

        return "report/dashboard";
    }
}