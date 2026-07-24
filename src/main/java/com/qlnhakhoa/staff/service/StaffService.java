package com.qlnhakhoa.staff.service;

import com.qlnhakhoa.staff.entity.Staff;
import com.qlnhakhoa.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Lấy tất cả nhân viên
    public List<Staff> getAllStaffs() {
        return staffRepository.findAll();
    }

    // Lưu / Cập nhật nhân viên
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // Lấy nhân viên theo ID
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    // Xóa nhân viên
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    // Tìm kiếm nhân viên
    public List<Staff> searchStaff(String keyword) {
        return staffRepository
                .findByStaffCodeContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}