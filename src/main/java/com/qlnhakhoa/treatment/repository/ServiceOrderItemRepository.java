package com.qlnhakhoa.treatment.repository;


import com.qlnhakhoa.treatment.entity.ServiceOrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {

    List<ServiceOrderItem> findByTreatmentId(Long treatmentId);

}

