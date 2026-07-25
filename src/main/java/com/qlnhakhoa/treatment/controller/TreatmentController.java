package com.qlnhakhoa.treatment.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import com.qlnhakhoa.treatment.entity.PrescriptionItem;
import com.qlnhakhoa.treatment.entity.ServiceOrderItem;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.treatment.service.TreatmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired(required = false)
    private InvoiceRepository invoiceRepository;

    // ================== DANH SÁCH BỆNH NHÂN CHỜ ==================

    @GetMapping("/treatment")
    public String waitingList(Model model) {
        model.addAttribute("appointments", treatmentService.getWaitingList());
        return "treatment/list";
    }

    // Tìm kiếm hồ sơ khám
    @GetMapping("/treatment/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("treatments", treatmentService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "treatment/search";
    }

    // ================== HỒ SƠ KHÁM ==================

    @GetMapping("/treatment/exam/{appointmentId}")
    public String exam(@PathVariable Long appointmentId, Model model) {
        Treatment treatment = treatmentService.getOrCreateTreatment(appointmentId);
        if (treatment == null) return "redirect:/treatment";

        model.addAttribute("treatment", treatment);
        model.addAttribute("prescriptionItem", new PrescriptionItem());
        model.addAttribute("serviceOrderItem", new ServiceOrderItem());

        return "treatment/exam";
    }

    // ================== CHẨN ĐOÁN ==================

    @PostMapping("/treatment/{id}/diagnosis")
    public String saveDiagnosis(@PathVariable Long id, 
                                @RequestParam("symptom") String symptom,
                                @RequestParam("diagnosis") String diagnosis, 
                                @RequestParam("note") String note) {
        treatmentService.saveDiagnosis(id, symptom, diagnosis, note);
        return "redirect:/treatment/exam/" + getAppointmentId(id);
    }

    // ================== KÊ THUỐC ==================

    @PostMapping("/treatment/{id}/medicine/add")
    public String addMedicine(@PathVariable Long id, @ModelAttribute PrescriptionItem prescriptionItem) {
        treatmentService.addPrescriptionItem(id, prescriptionItem);
        return "redirect:/treatment/exam/" + getAppointmentId(id);
    }

    @GetMapping("/treatment/{id}/medicine/delete/{itemId}")
    public String deleteMedicine(@PathVariable Long id, @PathVariable Long itemId) {
        treatmentService.deletePrescriptionItem(itemId);
        return "redirect:/treatment/exam/" + getAppointmentId(id);
    }

    // ================== CHỈ ĐỊNH DỊCH VỤ ==================

    @PostMapping("/treatment/{id}/service/add")
    public String addService(@PathVariable Long id, @ModelAttribute ServiceOrderItem serviceOrderItem) {
        treatmentService.addServiceOrderItem(id, serviceOrderItem);
        return "redirect:/treatment/exam/" + getAppointmentId(id);
    }

    @GetMapping("/treatment/{id}/service/delete/{itemId}")
    public String deleteService(@PathVariable Long id, @PathVariable Long itemId) {
        treatmentService.deleteServiceOrderItem(itemId);
        return "redirect:/treatment/exam/" + getAppointmentId(id);
    }

    // ================== HOÀN TẤT KHÁM & TẠO HÓA ĐƠN ==================

    @RequestMapping(value = "/treatment/{id}/complete", method = {RequestMethod.GET, RequestMethod.POST})
    public String complete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

        // 1. Thực thi service hoàn tất ca khám
        try {
            treatmentService.completeTreatment(id);
        } catch (Exception e) {
            System.err.println("Lỗi service treatment: " + e.getMessage());
        }

        Invoice targetInvoice = null;

        try {
            if (invoiceRepository != null) {
                Treatment treatment = treatmentService.getTreatmentById(id);

                String patientName = "Bệnh nhân #" + id;
                Long patientId = id;
                Double totalAmount = 0.0;
                StringBuilder itemsBuilder = new StringBuilder();

                if (treatment != null) {
                    if (treatment.getTotalAmount() != null) {
                        totalAmount = treatment.getTotalAmount();
                    }

                    if (treatment.getAppointment() != null && treatment.getAppointment().getPatient() != null) {
                        patientName = treatment.getAppointment().getPatient().getFullName();
                        patientId = treatment.getAppointment().getPatient().getId();
                    }

                    // GOM DỊCH VỤ
                    if (treatment.getServiceOrderItems() != null && !treatment.getServiceOrderItems().isEmpty()) {
                        for (ServiceOrderItem item : treatment.getServiceOrderItems()) {
                            if (itemsBuilder.length() > 0) itemsBuilder.append(" | ");
                            itemsBuilder.append("🛠️ ").append(item.getServiceName());
                            if (item.getQuantity() != null && item.getQuantity() > 1) {
                                itemsBuilder.append(" (x").append(item.getQuantity()).append(")");
                            }
                        }
                    }

                    // GOM THUỐC
                    if (treatment.getPrescriptionItems() != null && !treatment.getPrescriptionItems().isEmpty()) {
                        for (PrescriptionItem item : treatment.getPrescriptionItems()) {
                            if (itemsBuilder.length() > 0) itemsBuilder.append(" | ");
                            itemsBuilder.append("💊 ").append(item.getMedicineName());
                            if (item.getQuantity() != null) {
                                itemsBuilder.append(" (SL: ").append(item.getQuantity()).append(")");
                            }
                        }
                    }
                }

                String summaryText = itemsBuilder.length() > 0 ? itemsBuilder.toString() : "Khám tổng quát";

                // VÒNG LẶP CHỐNG LỖI "FINAL / EFFECTIVELY FINAL" LAMBDA
                List<Invoice> existingInvoices = invoiceRepository.findAll();
                if (existingInvoices != null) {
                    for (Invoice inv : existingInvoices) {
                        if (inv != null) {
                            boolean matchId = inv.getPatientId() != null && inv.getPatientId().equals(patientId);
                            boolean matchName = inv.getPatientName() != null && inv.getPatientName().equals(patientName);
                            if (matchId || matchName) {
                                targetInvoice = inv; // Lấy bản ghi khớp
                            }
                        }
                    }
                }

                // Nếu chưa từng có hóa đơn nào thì khởi tạo mới
                if (targetInvoice == null) {
                    targetInvoice = new Invoice(patientId, patientName, totalAmount);
                } else {
                    // Nếu đã có sẵn hóa đơn từ Service, chỉ cần cập nhật lại thông tin chuẩn
                    targetInvoice.setPatientId(patientId);
                    targetInvoice.setPatientName(patientName);
                    targetInvoice.setAmount(totalAmount);
                }

                targetInvoice.setItemsSummary(summaryText);
                targetInvoice = invoiceRepository.save(targetInvoice);
            }
        } catch (Exception e) {
            System.err.println("Lỗi xử lý hóa đơn: " + e.getMessage());
        }

        if (targetInvoice != null && targetInvoice.getId() != null) {
            redirectAttributes.addFlashAttribute("success", "Hoàn tất khám bệnh! Hóa đơn đã được tạo.");
            return "redirect:/invoice/checkout/" + targetInvoice.getId();
        }

        return "redirect:/invoice";
    }

    private Long getAppointmentId(Long treatmentId) {
        Treatment treatment = treatmentService.getTreatmentById(treatmentId);
        if (treatment != null && treatment.getAppointment() != null) {
            return treatment.getAppointment().getId();
        }
        return treatmentId;
    }
}