package com.example.demo_api.repository;

import com.example.demo_api.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    Optional<Material> findByNombre(String nombre);
}
