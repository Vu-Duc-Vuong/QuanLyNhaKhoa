package com.qlnhakhoa.treatment.controller;


import com.qlnhakhoa.treatment.entity.PrescriptionItem;
import com.qlnhakhoa.treatment.entity.ServiceOrderItem;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.treatment.service.TreatmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class TreatmentController {


    @Autowired
    private TreatmentService treatmentService;



    // ================== DANH SÁCH BỆNH NHÂN CHỜ ==================

    @GetMapping("/treatment")
    public String waitingList(Model model) {

        model.addAttribute(
                "appointments",
                treatmentService.getWaitingList()
        );

        return "treatment/list";

    }



    // Tìm kiếm hồ sơ khám (theo mã hồ sơ / tên bệnh nhân)
    @GetMapping("/treatment/search")
    public String search(
            @RequestParam("keyword") String keyword,
            Model model) {

        model.addAttribute(
                "treatments",
                treatmentService.search(keyword)
        );

        model.addAttribute("keyword", keyword);

        return "treatment/search";

    }



    // ================== HỒ SƠ KHÁM ==================

    // Mở hồ sơ khám cho 1 lịch hẹn (tự tạo nếu chưa có)
    @GetMapping("/treatment/exam/{appointmentId}")
    public String exam(
            @PathVariable Long appointmentId,
            Model model) {

        Treatment treatment =
                treatmentService.getOrCreateTreatment(appointmentId);

        if (treatment == null) {
            return "redirect:/treatment";
        }

        model.addAttribute("treatment", treatment);

        model.addAttribute("prescriptionItem", new PrescriptionItem());

        model.addAttribute("serviceOrderItem", new ServiceOrderItem());

        return "treatment/exam";

    }



    // ================== CHẨN ĐOÁN ==================

    @PostMapping("/treatment/{id}/diagnosis")
    public String saveDiagnosis(
            @PathVariable Long id,
            @RequestParam("symptom") String symptom,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("note") String note) {

        treatmentService.saveDiagnosis(id, symptom, diagnosis, note);

        return "redirect:/treatment/exam/" + getAppointmentId(id);

    }



    // ================== KÊ THUỐC ==================

    @PostMapping("/treatment/{id}/medicine/add")
    public String addMedicine(
            @PathVariable Long id,
            @ModelAttribute PrescriptionItem prescriptionItem) {

        treatmentService.addPrescriptionItem(id, prescriptionItem);

        return "redirect:/treatment/exam/" + getAppointmentId(id);

    }



    @GetMapping("/treatment/{id}/medicine/delete/{itemId}")
    public String deleteMedicine(
            @PathVariable Long id,
            @PathVariable Long itemId) {

        treatmentService.deletePrescriptionItem(itemId);

        return "redirect:/treatment/exam/" + getAppointmentId(id);

    }



    // ================== CHỈ ĐỊNH DỊCH VỤ ==================

    @PostMapping("/treatment/{id}/service/add")
    public String addService(
            @PathVariable Long id,
            @ModelAttribute ServiceOrderItem serviceOrderItem) {

        treatmentService.addServiceOrderItem(id, serviceOrderItem);

        return "redirect:/treatment/exam/" + getAppointmentId(id);

    }



    @GetMapping("/treatment/{id}/service/delete/{itemId}")
    public String deleteService(
            @PathVariable Long id,
            @PathVariable Long itemId) {

        treatmentService.deleteServiceOrderItem(itemId);

        return "redirect:/treatment/exam/" + getAppointmentId(id);

    }



    // ================== HOÀN TẤT KHÁM ==================

    @PostMapping("/treatment/{id}/complete")
    public String complete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        treatmentService.completeTreatment(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Hoàn tất khám bệnh! Hóa đơn đã được tạo."
        );

        return "redirect:/treatment";

    }



    // Lấy lại appointmentId từ treatmentId để quay lại đúng trang hồ sơ khám
    private Long getAppointmentId(Long treatmentId) {

        Treatment treatment = treatmentService.getTreatmentById(treatmentId);

        if (treatment != null && treatment.getAppointment() != null) {

            return treatment.getAppointment().getId();

        }

        return treatmentId;

    }


}
