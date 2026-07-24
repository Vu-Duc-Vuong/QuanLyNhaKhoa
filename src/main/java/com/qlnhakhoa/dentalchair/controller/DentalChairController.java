package com.qlnhakhoa.dentalchair.controller;

import com.qlnhakhoa.dentalchair.entity.DentalChair;
import com.qlnhakhoa.dentalchair.service.DentalChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DentalChairController {

    @Autowired
    private DentalChairService chairService;

    // Danh sách ghế nha khoa
    @GetMapping("/chair")
    public String chairList(Model model) {
        model.addAttribute(
                "chairs",
                chairService.getAllChairs()
        );
        return "dentalchair/list";
    }

    // Mở trang thêm ghế nha khoa
    @GetMapping("/chair/add")
    public String addChairPage(Model model) {
        model.addAttribute(
                "chair",
                new DentalChair()
        );
        return "dentalchair/add";
    }

    // Lưu thêm / cập nhật ghế
    @PostMapping("/chair/save")
    public String saveChair(@ModelAttribute DentalChair chair) {
        chairService.saveChair(chair);
        return "redirect:/chair";
    }

    // Mở trang sửa ghế nha khoa
    @GetMapping("/chair/edit/{id}")
    public String editChair(@PathVariable Long id, Model model) {
        DentalChair chair = chairService.getChairById(id);
        model.addAttribute(
                "chair",
                chair
        );
        return "dentalchair/edit";
    }

    // Xóa ghế nha khoa
    @GetMapping("/chair/delete/{id}")
    public String deleteChair(@PathVariable Long id) {
        chairService.deleteChair(id);
        return "redirect:/chair";
    }

    // Tìm kiếm ghế
    @GetMapping("/chair/search")
    public String searchChair(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "chairs",
                chairService.searchChair(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "dentalchair/list";
    }
}