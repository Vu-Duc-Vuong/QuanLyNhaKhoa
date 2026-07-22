package com.qlnhakhoa.appointment.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequestDTO {

    private Long doctorId;  // Mã bác sĩ
    private Long chairId;   // Mã ghế nha
    private LocalDate workDate; // Ngày khám
    private String shift;   // Ca làm việc: MORNING, AFTERNOON, EVENING
    private String note;    // Ghi chú
}