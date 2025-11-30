package com.example.demo_api.repository;

import com.example.demo_api.model.Asesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsesorRepository extends JpaRepository<Asesor, String> {
    Optional<Asesor> findByEmail(String email);
}
