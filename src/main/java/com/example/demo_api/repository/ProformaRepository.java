package com.example.demo_api.repository;

import com.example.demo_api.model.Proforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProformaRepository extends JpaRepository<Proforma, String> {
    List<Proforma> findByIdAreaConstruccion(String idAreaConstruccion);
}
