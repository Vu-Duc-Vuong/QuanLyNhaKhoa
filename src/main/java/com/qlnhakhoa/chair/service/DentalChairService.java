package com.qlnhakhoa.chair.service;

import com.qlnhakhoa.chair.entity.DentalChair;
import com.qlnhakhoa.chair.repository.DentalChairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentalChairService {

    @Autowired
    private DentalChairRepository repository;

    public List<DentalChair> getAll() {
        return repository.findAll();
    }

    public DentalChair getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ghế nha với ID: " + id));
    }

    public DentalChair create(DentalChair chair) {
        return repository.save(chair);
    }

    public DentalChair update(Long id, DentalChair details) {
        DentalChair chair = getById(id);
        chair.setName(details.getName());
        chair.setRoomNumber(details.getRoomNumber());
        chair.setStatus(details.getStatus());
        return repository.save(chair);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}