package com.qlnhakhoa.employee.service;

import com.qlnhakhoa.employee.entity.Employee;
import com.qlnhakhoa.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee update(Long id, Employee details) {
        Employee emp = getById(id);
        emp.setFullName(details.getFullName());
        emp.setRole(details.getRole());
        emp.setPhone(details.getPhone());
        emp.setEmail(details.getEmail());
        emp.setActive(details.getActive());
        return repository.save(emp);
    }

    public void delete(Long id) {
        Employee emp = getById(id);
        emp.setActive(false);
        repository.save(emp);
    }
}