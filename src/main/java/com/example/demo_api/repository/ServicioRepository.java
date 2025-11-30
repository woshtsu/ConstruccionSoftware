package com.example.demo_api.repository;

import com.example.demo_api.model.Servicio;
import com.example.demo_api.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, String> {
    List<Servicio> findByCliente(Persona cliente);
}
