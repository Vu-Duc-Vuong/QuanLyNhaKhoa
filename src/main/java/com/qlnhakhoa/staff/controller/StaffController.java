package com.qlnhakhoa.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qlnhakhoa.staff.entity.Staff;
import com.qlnhakhoa.staff.service.StaffService;

@Controller
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Danh sách nhân viên
    @GetMapping("/staff")
    public String staffList(Model model) {
        model.addAttribute(
                "staffs",
                staffService.getAllStaffs()
        );
        return "staff/list";
    }

    // Mở trang thêm nhân viên
    @GetMapping("/staff/add")
    public String addStaffPage(Model model) {
        model.addAttribute(
                "staff",
                new Staff()
        );
        return "staff/add";
    }

    // Lưu thêm / cập nhật nhân viên
    @PostMapping("/staff/save")
    public String saveStaff(@ModelAttribute Staff staff) {
        staffService.saveStaff(staff);
        return "redirect:/staff";
    }

    // Mở trang sửa nhân viên
    @GetMapping("/staff/edit/{id}")
    public String editStaff(@PathVariable Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        model.addAttribute(
                "staff",
                staff
        );
        return "staff/edit";
    }

    // Xóa nhân viên
    @GetMapping("/staff/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staff";
    }

    // Tìm kiếm nhân viên
    @GetMapping("/staff/search")
    public String searchStaff(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "staffs",
                staffService.searchStaff(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "staff/list";
    }
}