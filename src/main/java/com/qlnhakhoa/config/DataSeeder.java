package com.qlnhakhoa.config;

import com.qlnhakhoa.appointment.entity.Appointment;
import com.qlnhakhoa.appointment.repository.AppointmentRepository;
import com.qlnhakhoa.medicine.entity.Medicine;
import com.qlnhakhoa.medicine.repository.MedicineRepository;
import com.qlnhakhoa.patient.entity.Patient;
import com.qlnhakhoa.patient.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabaseData(
            MedicineRepository medicineRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository) {
        return args -> {

            // 1. KHÔI PHỤC BỆNH NHÂN (LẤY TỪ ẢNH BẠN CUNG CẤP)
            if (patientRepository.count() == 0) {
                Patient p1 = new Patient();
                p1.setPatientCode("123");
                p1.setFullName("lonh");
                p1.setPhone("0399037172");
                p1.setGender("Nam");
                p1.setAddress("hà đông");

                Patient p2 = new Patient();
                p2.setPatientCode("24100383");
                p2.setFullName("Vương Đức Vũ");
                p2.setPhone("0900100809");
                p2.setGender("Nam");
                p2.setAddress("Tuyên Quang");

                Patient p3 = new Patient();
                p3.setPatientCode("5");
                p3.setFullName("Quân");
                p3.setPhone("0393232323");
                p3.setGender("Nam");
                p3.setAddress("hà đông");

                patientRepository.saveAll(List.of(p1, p2, p3));
            }

            // 2. KHÔI PHỤC LỊCH HẸN & BỆNH NHÂN CHỜ KHÁM (LẤY TỪ ẢNH BẠN CUNG CẤP)
            if (appointmentRepository.count() == 0) {
                List<Patient> patients = patientRepository.findAll();
                Patient pLonh = patients.stream().filter(p -> "123".equals(p.getPatientCode())).findFirst().orElse(null);
                Patient pVu = patients.stream().filter(p -> "24100383".equals(p.getPatientCode())).findFirst().orElse(null);

                if (pLonh != null) {
                    // Lịch hẹn 1 của lonh
                    Appointment a1 = new Appointment();
                    a1.setAppointmentCode("3");
                    a1.setPatient(pLonh);
                    a1.setAppointmentDate(LocalDate.of(2026, 12, 12));
                    a1.setAppointmentTime(LocalTime.of(12, 23));
                    a1.setStatus("Hoàn thành"); // hoặc "HOAN_THANH" tùy thuộc enum/string trong Entity của bạn

                    // Lịch hẹn 2 của lonh
                    Appointment a2 = new Appointment();
                    a2.setAppointmentCode("2");
                    a2.setPatient(pLonh);
                    a2.setAppointmentDate(LocalDate.of(2026, 12, 31));
                    a2.setAppointmentTime(LocalTime.of(3, 33));
                    a2.setStatus("Hoàn thành");

                    appointmentRepository.saveAll(List.of(a1, a2));
                }

                if (pVu != null) {
                    // Lịch hẹn 1 của Vương Đức Vũ (Đang chờ khám)
                    Appointment a3 = new Appointment();
                    a3.setAppointmentCode("7232026");
                    a3.setPatient(pVu);
                    a3.setAppointmentDate(LocalDate.of(2026, 7, 23));
                    a3.setAppointmentTime(LocalTime.of(12, 0));
                    a3.setStatus("Đang khám"); // hoặc "DANG_KHAM" / "CHO_KHAM"

                    // Lịch hẹn 2 của Vương Đức Vũ (Đang chờ khám)
                    Appointment a4 = new Appointment();
                    a4.setAppointmentCode("8447232026");
                    a4.setPatient(pVu);
                    a4.setAppointmentDate(LocalDate.of(2026, 7, 23));
                    a4.setAppointmentTime(LocalTime.of(20, 46));
                    a4.setStatus("Đang khám");

                    appointmentRepository.saveAll(List.of(a3, a4));
                }
            }

            // 3. KHÔI PHỤC DANH SÁCH THUỐC
            if (medicineRepository.count() == 0) {
                Medicine m1 = new Medicine();
                m1.setMedicineCode("MED-001");
                m1.setMedicineName("Paracetamol 500mg (Giảm đau, hạ sốt)");
                m1.setUnit("Viên");
                m1.setPrice(1500.0);
                m1.setQuantity(500);

                Medicine m2 = new Medicine();
                m2.setMedicineCode("MED-002");
                m2.setMedicineName("Amoxicillin 500mg (Kháng sinh)");
                m2.setUnit("Viên");
                m2.setPrice(2500.0);
                m2.setQuantity(300);

                Medicine m3 = new Medicine();
                m3.setMedicineCode("MED-003");
                m3.setMedicineName("Ibuprofen 400mg (Giảm đau, kháng viêm)");
                m3.setUnit("Viên");
                m3.setPrice(2000.0);
                m3.setQuantity(400);

                Medicine m4 = new Medicine();
                m4.setMedicineCode("MED-004");
                m4.setMedicineName("Rodogyl (Kháng sinh đặc trị răng miệng)");
                m4.setUnit("Viên");
                m4.setPrice(6000.0);
                m4.setQuantity(200);

                Medicine m5 = new Medicine();
                m5.setMedicineCode("MED-005");
                m5.setMedicineName("Gengigel (Gel bôi nhiệt miệng, viêm nướu)");
                m5.setUnit("Tuýp");
                m5.setPrice(290000.0);
                m5.setQuantity(30);

                Medicine m6 = new Medicine();
                m6.setMedicineCode("MED-006");
                m6.setMedicineName("Nước súc miệng Chlorhexidine 0.2%");
                m6.setUnit("Chai");
                m6.setPrice(85000.0);
                m6.setQuantity(50);

                medicineRepository.saveAll(List.of(m1, m2, m3, m4, m5, m6));
            }
        };
    }
}