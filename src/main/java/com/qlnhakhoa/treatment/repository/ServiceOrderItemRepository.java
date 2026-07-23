package com.qlnhakhoa.treatment.repository;


import com.qlnhakhoa.treatment.entity.ServiceOrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceOrderItemRepository extends JpaRepository<ServiceOrderItem, Long> {
}
