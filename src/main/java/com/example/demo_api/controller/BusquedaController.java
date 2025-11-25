package com.example.demo_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BusquedaController {
    private final com.example.demo_api.service.ScraperService scraperService;

    public BusquedaController(com.example.demo_api.service.ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping(path = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.List<com.example.demo_api.dto.ProductoListadoDTO>> buscar(@RequestParam("query") String query) {
        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.List.of());
        }
        try {
            var list = scraperService.buscarListadoFalabella(query);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(java.util.List.of());
        }
    }
}
