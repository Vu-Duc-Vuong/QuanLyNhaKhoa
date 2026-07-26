package com.qlnhakhoa.treatment.repository;


import com.qlnhakhoa.treatment.entity.PrescriptionItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {

    List<PrescriptionItem> findByTreatmentId(Long treatmentId);

}
