package com.qlnhakhoa.dentalchair.repository;

import com.qlnhakhoa.dentalchair.entity.DentalChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentalChairRepository extends JpaRepository<DentalChair, Long> {

    // Tìm kiếm theo mã ghế hoặc tên ghế
    List<DentalChair> findByChairCodeContainingIgnoreCaseOrChairNameContainingIgnoreCase(
            String chairCode,
            String chairName
    );
}