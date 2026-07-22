package com.qlnhakhoa.chair.controller;

import com.qlnhakhoa.chair.entity.DentalChair;
import com.qlnhakhoa.chair.service.DentalChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chairs")
public class DentalChairController {

    @Autowired
    private DentalChairService service;

    @GetMapping
    public ResponseEntity<List<DentalChair>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentalChair> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<DentalChair> create(@RequestBody DentalChair chair) {
        return ResponseEntity.ok(service.create(chair));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentalChair> update(@PathVariable Long id, @RequestBody DentalChair details) {
        return ResponseEntity.ok(service.update(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}