package com.example.demo_api.repository;

import com.example.demo_api.model.AreaConstruccion;
import com.example.demo_api.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaConstruccionRepository extends JpaRepository<AreaConstruccion, String> {
    List<AreaConstruccion> findByProyecto(Proyecto proyecto);
}
