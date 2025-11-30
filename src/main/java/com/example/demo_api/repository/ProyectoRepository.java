package com.example.demo_api.repository;

import com.example.demo_api.model.Proyecto;
import com.example.demo_api.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, String> {
    List<Proyecto> findByServicio(Servicio servicio);
}
