package com.example.demo_api.repository;

import com.example.demo_api.model.DetalleProforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleProformaRepository extends JpaRepository<DetalleProforma, String> {
}
