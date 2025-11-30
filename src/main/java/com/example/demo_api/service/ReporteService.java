package com.example.demo_api.service;

import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    public ReporteService() {
    }

    public java.util.List<com.example.demo_api.dto.ReporteDTO> listarPorPersona(String idPersona) {
        try {
            // TODO: Implementar búsqueda de reportes por persona cuando esté disponible la relación
            return java.util.List.of();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
