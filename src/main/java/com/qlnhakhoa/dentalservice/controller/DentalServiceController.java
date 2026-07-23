package com.qlnhakhoa.dentalservice.controller;

import com.qlnhakhoa.dentalservice.entity.DentalService;
import com.qlnhakhoa.dentalservice.service.DentalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class DentalServiceController {

    @Autowired
    private DentalServiceService service;

    @GetMapping
    public ResponseEntity<List<DentalService>> getAll() {
        return ResponseEntity.ok(service.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentalService> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<DentalService> create(@RequestBody DentalService dentalService) {
        return ResponseEntity.ok(service.create(dentalService));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentalService> update(@PathVariable Long id, @RequestBody DentalService details) {
        return ResponseEntity.ok(service.update(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}