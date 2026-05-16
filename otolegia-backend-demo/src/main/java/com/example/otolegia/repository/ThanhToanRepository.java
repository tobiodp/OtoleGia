package com.example.otolegia.repository;

import com.example.otolegia.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {
    Optional<ThanhToan> findByDonThueId(Long donThueId);
    boolean existsByDonThueId(Long donThueId);
}
