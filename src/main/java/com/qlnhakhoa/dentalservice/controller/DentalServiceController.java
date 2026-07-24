package com.qlnhakhoa.dentalservice.controller;

import com.qlnhakhoa.dentalservice.entity.DentalService;
import com.qlnhakhoa.dentalservice.service.DentalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DentalServiceController {

    @Autowired
    private DentalServiceService dentalServiceService;

    // Danh sách dịch vụ
    @GetMapping("/service")
    public String serviceList(Model model) {
        model.addAttribute(
                "services",
                dentalServiceService.getAllServices()
        );
        return "dentalservice/list";
    }

    // Mở trang thêm dịch vụ
    @GetMapping("/service/add")
    public String addServicePage(Model model) {
        model.addAttribute(
                "serviceItem",
                new DentalService()
        );
        return "dentalservice/add";
    }

    // Lưu thêm / cập nhật dịch vụ
    @PostMapping("/service/save")
    public String saveService(@ModelAttribute("serviceItem") DentalService serviceItem) {
        dentalServiceService.saveService(serviceItem);
        return "redirect:/service";
    }

    // Mở trang sửa dịch vụ
    @GetMapping("/service/edit/{id}")
    public String editService(@PathVariable Long id, Model model) {
        DentalService serviceItem = dentalServiceService.getServiceById(id);
        model.addAttribute(
                "serviceItem",
                serviceItem
        );
        return "dentalservice/edit";
    }

    // Xóa dịch vụ
    @GetMapping("/service/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        dentalServiceService.deleteService(id);
        return "redirect:/service";
    }

    // Tìm kiếm dịch vụ
    @GetMapping("/service/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute(
                "services",
                dentalServiceService.searchService(keyword)
        );
        model.addAttribute(
                "keyword",
                keyword
        );
        return "dentalservice/list";
    }
}