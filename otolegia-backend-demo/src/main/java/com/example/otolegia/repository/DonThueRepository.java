package com.example.otolegia.repository;

import com.example.otolegia.entity.DonThue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonThueRepository extends JpaRepository<DonThue, Long> {
    List<DonThue> findByKhachHangIdOrderByNgayTaoDesc(Long khachHangId);
}
