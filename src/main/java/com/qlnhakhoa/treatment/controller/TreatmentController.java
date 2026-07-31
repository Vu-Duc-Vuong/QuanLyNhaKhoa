package com.qlnhakhoa.treatment.controller;

import com.qlnhakhoa.invoice.entity.Invoice;
import com.qlnhakhoa.invoice.repository.InvoiceRepository;
import com.qlnhakhoa.treatment.entity.PrescriptionItem;
import com.qlnhakhoa.treatment.entity.ServiceOrderItem;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.medicine.service.MedicineService;
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


    @Autowired
    private MedicineService medicineService;


    @Autowired(required = false)
    private InvoiceRepository invoiceRepository;



    // ================== DANH SÁCH BỆNH NHÂN CHỜ ==================

    @GetMapping("/treatment")
    public String waitingList(Model model) {

        model.addAttribute(
                "appointments",
                treatmentService.getWaitingList()
        );

        return "treatment/list";
    }



    // ================== LỊCH SỬ KHÁM ==================

    @GetMapping("/treatment/history")
    public String history(Model model) {

        model.addAttribute(
                "treatments",
                treatmentService.getAllTreatments()
        );

        return "treatment/search";
    }



    @GetMapping("/treatment/delete/{id}")
    public String deleteTreatment(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        treatmentService.deleteTreatment(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Đã xóa hồ sơ khám bệnh."
        );

        return "redirect:/treatment/history";
    }




    // ================== TÌM KIẾM ==================

    @GetMapping("/treatment/search")
    public String search(
            @RequestParam("keyword") String keyword,
            Model model
    ) {

        model.addAttribute(
                "treatments",
                treatmentService.search(keyword)
        );

        model.addAttribute(
                "keyword",
                keyword
        );


        return "treatment/search";
    }




    // ================== HỒ SƠ KHÁM ==================

    @GetMapping("/treatment/exam/{appointmentId}")
    public String exam(
            @PathVariable Long appointmentId,
            Model model
    ) {


        Treatment treatment =
                treatmentService.getOrCreateTreatment(appointmentId);


        if(treatment == null){

            return "redirect:/treatment";

        }



        model.addAttribute(
                "treatment",
                treatment
        );


        model.addAttribute(
                "prescriptionItem",
                new PrescriptionItem()
        );


        model.addAttribute(
                "serviceOrderItem",
                new ServiceOrderItem()
        );


        model.addAttribute(
                "medicines",
                medicineService.getAllMedicines()
        );



        return "treatment/exam";

    }





    // ================== CHẨN ĐOÁN ==================


    @PostMapping("/treatment/{id}/diagnosis")
    public String saveDiagnosis(
            @PathVariable Long id,
            @RequestParam("symptom") String symptom,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("note") String note
    ){


        treatmentService.saveDiagnosis(
                id,
                symptom,
                diagnosis,
                note
        );


        return "redirect:/treatment/exam/" 
                + getAppointmentId(id);

    }





    // ================== KÊ THUỐC ==================


    @PostMapping("/treatment/{id}/medicine/add")
    public String addMedicine(
            @PathVariable Long id,
            @ModelAttribute PrescriptionItem prescriptionItem
    ){


        treatmentService.addPrescriptionItem(
                id,
                prescriptionItem
        );


        return "redirect:/treatment/exam/"
                + getAppointmentId(id);

    }



    @GetMapping("/treatment/{id}/medicine/delete/{itemId}")
    public String deleteMedicine(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){


        treatmentService.deletePrescriptionItem(itemId);


        return "redirect:/treatment/exam/"
                + getAppointmentId(id);

    }





    // ================== DỊCH VỤ ==================


    @PostMapping("/treatment/{id}/service/add")
    public String addService(
            @PathVariable Long id,
            @ModelAttribute ServiceOrderItem serviceOrderItem
    ){


        treatmentService.addServiceOrderItem(
                id,
                serviceOrderItem
        );


        return "redirect:/treatment/exam/"
                + getAppointmentId(id);

    }



    @GetMapping("/treatment/{id}/service/delete/{itemId}")
    public String deleteService(
            @PathVariable Long id,
            @PathVariable Long itemId
    ){


        treatmentService.deleteServiceOrderItem(itemId);


        return "redirect:/treatment/exam/"
                + getAppointmentId(id);

    }
    // ================== HOÀN TẤT KHÁM & TẠO HÓA ĐƠN ==================

    @PostMapping("/treatment/{id}/complete")
    public String complete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {


        Treatment treatment =
                treatmentService.getTreatmentById(id);



        // cập nhật trạng thái hoàn thành
        treatmentService.completeTreatment(id);



        Invoice targetInvoice = null;



        try {


            if(invoiceRepository != null){


                treatment =
                        treatmentService.getTreatmentById(id);



                String patientName =
                        "Bệnh nhân #" + id;


                Long patientId = id;


                Double totalAmount = 0.0;



                StringBuilder itemsBuilder =
                        new StringBuilder();




                if(treatment != null){



                    if(treatment.getTotalAmount() != null){

                        totalAmount =
                                treatment.getTotalAmount();

                    }




                    if(treatment.getAppointment() != null
                            && treatment.getAppointment().getPatient() != null){


                        patientName =
                                treatment.getAppointment()
                                        .getPatient()
                                        .getFullName();


                        patientId =
                                treatment.getAppointment()
                                        .getPatient()
                                        .getId();

                    }





                    if(treatment.getServiceOrderItems() != null){


                        for(ServiceOrderItem item :
                                treatment.getServiceOrderItems()){


                            if(itemsBuilder.length() > 0){

                                itemsBuilder.append(" | ");

                            }


                            itemsBuilder.append(
                                    item.getServiceName()
                            );


                        }

                    }





                    if(treatment.getPrescriptionItems() != null){


                        for(PrescriptionItem item :
                                treatment.getPrescriptionItems()){


                            if(itemsBuilder.length() > 0){

                                itemsBuilder.append(" | ");

                            }


                            itemsBuilder.append(
                                    item.getMedicineName()
                            );


                        }

                    }


                }



                String summary =
                        itemsBuilder.length() > 0
                                ?
                                itemsBuilder.toString()
                                :
                                "Khám tổng quát";




                targetInvoice =
                        new Invoice(
                                patientId,
                                patientName,
                                totalAmount
                        );



                targetInvoice.setItemsSummary(summary);



                targetInvoice.setPaymentStatus(
                        "UNPAID"
                );



                targetInvoice =
                        invoiceRepository.save(targetInvoice);



            }



        }catch(Exception e){


            System.err.println(
                    "Lỗi tạo hóa đơn: "
                            + e.getMessage()
            );


        }




        if(targetInvoice != null
                && targetInvoice.getId() != null){



            redirectAttributes.addFlashAttribute(
                    "success",
                    "Hoàn tất khám bệnh! Hóa đơn đã được tạo."
            );



            return "redirect:/invoice/checkout/"
                    + targetInvoice.getId();


        }



        return "redirect:/invoice";


    }






    // ================== CẬP NHẬT HỒ SƠ SAU KHI HOÀN THÀNH ==================


    @PostMapping("/treatment/{id}/update")
    public String updateTreatment(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ){



        treatmentService.updateTreatment(id);



        redirectAttributes.addFlashAttribute(
                "success",
                "Đã cập nhật lại hồ sơ khám bệnh."
        );



        return "redirect:/treatment/exam/"
                + getAppointmentId(id);


    }







    // ================== LẤY APPOINTMENT ID ==================

    private Long getAppointmentId(Long treatmentId){



        Treatment treatment =
                treatmentService.getTreatmentById(treatmentId);



        if(treatment != null
                && treatment.getAppointment() != null){


            return treatment.getAppointment().getId();

        }



        return treatmentId;


    }


}