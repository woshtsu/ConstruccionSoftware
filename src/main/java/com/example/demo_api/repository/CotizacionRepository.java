package com.example.demo_api.repository;

import com.example.demo_api.model.Cotizacion;
import com.example.demo_api.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, String> {
    List<Cotizacion> findByMaterial(Material material);
}
