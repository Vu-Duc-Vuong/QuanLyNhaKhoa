package com.qlnhakhoa.treatment.service;


import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.invoice.service.InvoiceService;
import com.qlnhakhoa.treatment.entity.PrescriptionItem;
import com.qlnhakhoa.treatment.entity.ServiceOrderItem;
import com.qlnhakhoa.treatment.entity.Treatment;
import com.qlnhakhoa.treatment.repository.PrescriptionItemRepository;
import com.qlnhakhoa.treatment.repository.ServiceOrderItemRepository;
import com.qlnhakhoa.treatment.repository.TreatmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class TreatmentService {


    // Trạng thái lịch hẹn khi bệnh nhân đang ở phòng khám, chờ bác sĩ xử lý hồ sơ
    private static final String STATUS_DANG_KHAM = "Đang khám";

    // Trạng thái khi đã hoàn tất khám
    private static final String STATUS_HOAN_THANH = "Hoàn thành";



    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    private ServiceOrderItemRepository serviceOrderItemRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private InvoiceService invoiceService;



    // ================== DANH SÁCH BỆNH NHÂN CHỜ ==================

    // Lấy danh sách lịch hẹn đang chờ xử lý tại phòng khám (status = "Đang khám")
    public List<Appointment> getWaitingList() {

        return appointmentRepository.findByStatus(STATUS_DANG_KHAM);

    }



    // ================== HỒ SƠ KHÁM ==================

    // Lấy hồ sơ khám của 1 lịch hẹn, nếu chưa có thì tự tạo mới
    public Treatment getOrCreateTreatment(Long appointmentId) {

        return treatmentRepository
                .findByAppointmentId(appointmentId)
                .orElseGet(() -> createTreatment(appointmentId));

    }



    private Treatment createTreatment(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElse(null);

        if (appointment == null) {
            return null;
        }

        Treatment treatment = new Treatment();

        treatment.setAppointment(appointment);

        treatment.setTreatmentCode("KB-" + appointment.getAppointmentCode());

        treatment.setStatus(STATUS_DANG_KHAM);

        treatment.setCreatedDate(LocalDate.now());

        return treatmentRepository.save(treatment);

    }



    public Treatment getTreatmentById(Long id) {

        return treatmentRepository
                .findById(id)
                .orElse(null);

    }



    // Tìm kiếm hồ sơ khám theo mã hồ sơ hoặc tên bệnh nhân
    public List<Treatment> search(String keyword) {

        return treatmentRepository
                .findByTreatmentCodeContainingIgnoreCaseOrAppointment_Patient_FullNameContainingIgnoreCase(
                        keyword,
                        keyword
                );

    }



    // ================== CHẨN ĐOÁN ==================

    public void saveDiagnosis(Long treatmentId, String symptom, String diagnosis, String note) {

        Treatment treatment = getTreatmentById(treatmentId);

        if (treatment == null) {
            return;
        }

        treatment.setSymptom(symptom);
        treatment.setDiagnosis(diagnosis);
        treatment.setNote(note);

        treatmentRepository.save(treatment);

    }



    // ================== KÊ THUỐC ==================

    public void addPrescriptionItem(Long treatmentId, PrescriptionItem item) {

        Treatment treatment = getTreatmentById(treatmentId);

        if (treatment == null) {
            return;
        }

        item.setTreatment(treatment);

        treatment.getPrescriptionItems().add(item);

        treatmentRepository.save(treatment);

    }



    public void deletePrescriptionItem(Long itemId) {

        prescriptionItemRepository.deleteById(itemId);

    }



    // ================== CHỈ ĐỊNH DỊCH VỤ ==================

    public void addServiceOrderItem(Long treatmentId, ServiceOrderItem item) {

        Treatment treatment = getTreatmentById(treatmentId);

        if (treatment == null) {
            return;
        }

        item.setTreatment(treatment);

        treatment.getServiceOrderItems().add(item);

        recalculateTotal(treatment);

        treatmentRepository.save(treatment);

    }



    public void deleteServiceOrderItem(Long itemId) {

        serviceOrderItemRepository
                .findById(itemId)
                .ifPresent(item -> {

                    Treatment treatment = item.getTreatment();

                    serviceOrderItemRepository.deleteById(itemId);

                    if (treatment != null) {

                        treatment.getServiceOrderItems()
                                .removeIf(i -> i.getId().equals(itemId));

                        recalculateTotal(treatment);

                        treatmentRepository.save(treatment);

                    }

                });

    }



    private void recalculateTotal(Treatment treatment) {

        double total = 0.0;

        for (ServiceOrderItem item : treatment.getServiceOrderItems()) {

            total += item.getLineTotal();

        }

        treatment.setTotalAmount(total);

    }



    // ================== HOÀN TẤT KHÁM ==================

    public Treatment completeTreatment(Long treatmentId) {

        Treatment treatment = getTreatmentById(treatmentId);

        if (treatment == null) {
            return null;
        }

        recalculateTotal(treatment);

        treatment.setStatus(STATUS_HOAN_THANH);

        treatment.setCompletedDate(LocalDate.now());

        treatmentRepository.save(treatment);


        // Cập nhật trạng thái lịch hẹn tương ứng
        Appointment appointment = treatment.getAppointment();

        if (appointment != null) {

            appointment.setStatus(STATUS_HOAN_THANH);

            appointmentRepository.save(appointment);

        }


        // Tạo hóa đơn thanh toán cho lượt khám này
        String patientName = (appointment != null && appointment.getPatient() != null)
                ? appointment.getPatient().getFullName()
                : "Khách hàng";

        invoiceService.createInvoice(patientName, treatment.getTotalAmount());


        return treatment;

    }


}
