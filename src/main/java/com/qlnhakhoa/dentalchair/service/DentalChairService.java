package com.qlnhakhoa.dentalchair.service;

import com.qlnhakhoa.dentalchair.entity.DentalChair;
import com.qlnhakhoa.dentalchair.repository.DentalChairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentalChairService {

    @Autowired
    private DentalChairRepository chairRepository;

    // Lấy tất cả ghế nha khoa
    public List<DentalChair> getAllChairs() {
        return chairRepository.findAll();
    }

    // Lưu / Cập nhật ghế
    public DentalChair saveChair(DentalChair chair) {
        return chairRepository.save(chair);
    }

    // Lấy ghế theo ID
    public DentalChair getChairById(Long id) {
        return chairRepository.findById(id).orElse(null);
    }

    // Xóa ghế
    public void deleteChair(Long id) {
        chairRepository.deleteById(id);
    }

    // Tìm kiếm ghế
    public List<DentalChair> searchChair(String keyword) {
        return chairRepository
                .findByChairCodeContainingIgnoreCaseOrChairNameContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}