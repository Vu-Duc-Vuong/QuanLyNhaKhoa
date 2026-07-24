package com.qlnhakhoa.doctorschedule.controller;

import com.qlnhakhoa.doctorschedule.entity.DoctorSchedule;
import com.qlnhakhoa.doctorschedule.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService scheduleService;

    // Danh sách lịch làm việc của bác sĩ
    @GetMapping("/schedule")
    public String scheduleList(Model model) {
        model.addAttribute(
                "schedules",
                scheduleService.getAllSchedules()
        );
        return "doctorschedule/list";
    }

    // Mở trang thêm lịch trực
    @GetMapping("/schedule/add")
    public String addSchedulePage(Model model) {
        model.addAttribute(
                "schedule",
                new DoctorSchedule()
        );
        return "doctorschedule/add";
    }

    // Lưu thêm / cập nhật lịch trực
    @PostMapping("/schedule/save")
    public String saveSchedule(@ModelAttribute("schedule") DoctorSchedule schedule) {
        scheduleService.saveSchedule(schedule);
        return "redirect:/schedule";
    }

    // Mở trang sửa lịch trực
    @GetMapping("/schedule/edit/{id}")
    public String editSchedule(@PathVariable Long id, Model model) {
        DoctorSchedule schedule = scheduleService.getScheduleById(id);
        model.addAttribute(
                "schedule",
                schedule
        );
        return "doctorschedule/edit";
    }

    // Xóa lịch trực
    @GetMapping("/schedule/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedule";
    }

    // Tìm kiếm lịch trực
    @GetMapping("/schedule/search")
    public String searchSchedule(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "schedules",
                scheduleService.searchSchedule(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "doctorschedule/list";
    }
}