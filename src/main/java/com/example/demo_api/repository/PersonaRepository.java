package com.example.demo_api.repository;

import com.example.demo_api.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {
    Optional<Persona> findByEmail(String email);
}
