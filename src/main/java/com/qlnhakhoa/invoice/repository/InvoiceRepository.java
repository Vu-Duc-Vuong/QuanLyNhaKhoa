package com.qlnhakhoa.invoice.repository;


import com.qlnhakhoa.invoice.entity.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface InvoiceRepository 
        extends JpaRepository<Invoice, Long> {



    // Tính tổng doanh thu
    @Query("SELECT SUM(i.amount) FROM Invoice i")
    Double sumTotalAmount();



}