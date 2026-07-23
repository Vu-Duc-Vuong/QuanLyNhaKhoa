package com.qlnhakhoa.employee.controller;

import com.qlnhakhoa.employee.dto.AssignRoleRequestDTO;
import com.qlnhakhoa.employee.dto.EmployeeResponseDTO;
import com.qlnhakhoa.employee.dto.RegisterRequestDTO;
import com.qlnhakhoa.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Endpoint 1: Người dùng Đăng ký tài khoản công khai (Tự động nhận Role = CHO_DUYET)
    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        return new ResponseEntity<>(service.register(dto), HttpStatus.CREATED);
    }

    // Endpoint 2: Admin lấy danh sách tài khoản ĐANG CHỜ DUYỆT
    @GetMapping("/pending")
    public ResponseEntity<List<EmployeeResponseDTO>> getPendingEmployees() {
        return ResponseEntity.ok(service.getPendingEmployees());
    }

    // Endpoint 3: Admin Duyệt và Gán Role (Đổi role từ CHO_DUYET thành LE_TAN, BAC_SI, ADMIN)
    @PutMapping("/{id}/assign-role")
    public ResponseEntity<EmployeeResponseDTO> assignRole(
            @PathVariable Long id,
            @RequestBody AssignRoleRequestDTO dto) {
        return ResponseEntity.ok(service.assignRole(id, dto));
    }

    // Endpoint 4: Admin xem toàn bộ danh sách người dùng/nhân sự
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    // Endpoint 5: Xem thông tin 1 tài khoản
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Endpoint 6: Admin xóa/từ chối tài khoản
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}