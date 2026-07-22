package com.qlnhakhoa.invoice.service;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(String patientName, Double amount) {
        Invoice invoice = new Invoice(patientName, amount, "PAID", LocalDate.now());
        return invoiceRepository.save(invoice);
    }
}