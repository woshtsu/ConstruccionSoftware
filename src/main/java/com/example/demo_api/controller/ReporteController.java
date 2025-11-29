package com.example.demo_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReporteController {
    private final com.example.demo_api.service.ReporteService reporteService;

    public ReporteController(com.example.demo_api.service.ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping(path = "/personas/{id}/reportes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.List<com.example.demo_api.dto.ReporteDTO>> listarPorPersona(@PathVariable("id") String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.List.of());
        }
        return ResponseEntity.ok(reporteService.listarPorPersona(id));
    }
}
