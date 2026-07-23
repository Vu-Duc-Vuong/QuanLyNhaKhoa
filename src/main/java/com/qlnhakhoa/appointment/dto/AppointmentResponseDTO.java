package com.qlnhakhoa.appointment.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDTO {

    private Long id;
    private Long doctorId;
    private String doctorName;  // Trả luôn tên bác sĩ để Frontend dễ hiển thị
    private Long chairId;
    private String chairName;   // Tên ghế
    private String roomNumber;  // Số phòng
    private LocalDate workDate;
    private String shift;
    private String note;
}