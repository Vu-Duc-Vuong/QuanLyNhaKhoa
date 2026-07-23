package com.qlnhakhoa.treatment.repository;


import com.qlnhakhoa.treatment.entity.PrescriptionItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
}
