package com.qlnhakhoa.employee.service;

import com.qlnhakhoa.employee.dto.AssignRoleRequestDTO;
import com.qlnhakhoa.employee.dto.EmployeeResponseDTO;
import com.qlnhakhoa.employee.dto.RegisterRequestDTO;
import com.qlnhakhoa.employee.entity.Employee;
import com.qlnhakhoa.employee.entity.Role;
import com.qlnhakhoa.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // 1. Đăng ký tài khoản mới (Mặc định Role = CHO_DUYET)
    public EmployeeResponseDTO register(RegisterRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trên hệ thống!");
        }
        if (repository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Số điện thoại đã tồn tại trên hệ thống!");
        }

        Employee employee = Employee.builder()
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Lưu ý: thực tế nên mã hóa bằng PasswordEncoder
                .role(Role.CHO_DUYET)         // Luôn đặt mặc định là CHO_DUYET
                .build();

        return convertToResponseDTO(repository.save(employee));
    }

    // 2. Lấy danh sách các tài khoản đang CHỜ DUYỆT
    public List<EmployeeResponseDTO> getPendingEmployees() {
        return repository.findByRole(Role.CHO_DUYET).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // 3. Admin Duyệt tài khoản & Gán Role (LE_TAN, BAC_SI, ADMIN)
    public EmployeeResponseDTO assignRole(Long id, AssignRoleRequestDTO dto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + id));

        if (dto.getNewRole() == Role.CHO_DUYET) {
            throw new RuntimeException("Vui lòng chọn vai trò hợp lệ (LE_TAN, BAC_SI, ADMIN)!");
        }

        employee.setRole(dto.getNewRole());
        return convertToResponseDTO(repository.save(employee));
    }

    // 4. Lấy tất cả nhân viên / người dùng
    public List<EmployeeResponseDTO> getAllEmployees() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // 5. Lấy chi tiết theo ID
    public EmployeeResponseDTO getById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + id));
        return convertToResponseDTO(employee);
    }

    // 6. Xóa/Từ chối tài khoản
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tài khoản để xóa!");
        }
        repository.deleteById(id);
    }

    // Convert Entity -> Response DTO
    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .role(employee.getRole())
                .build();
    }
}