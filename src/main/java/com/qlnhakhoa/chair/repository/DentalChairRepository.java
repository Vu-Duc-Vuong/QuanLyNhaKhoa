package com.qlnhakhoa.chair.repository;

import com.qlnhakhoa.chair.entity.DentalChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentalChairRepository extends JpaRepository<DentalChair, Long> {
    List<DentalChair> findByStatus(String status);
}