package com.example.demo_api.repository;

import com.example.demo_api.model.MaterialProforma;
import com.example.demo_api.model.Proforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialProformaRepository extends JpaRepository<MaterialProforma, String> {
    List<MaterialProforma> findByProforma(Proforma proforma);
}
