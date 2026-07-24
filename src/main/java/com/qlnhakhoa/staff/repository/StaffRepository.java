package com.qlnhakhoa.staff.repository;

import com.qlnhakhoa.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // Tìm kiếm theo mã nhân viên hoặc họ tên
    List<Staff> findByStaffCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(
            String staffCode,
            String fullName
    );
}