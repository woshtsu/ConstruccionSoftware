package com.example.demo_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CotizacionController {
    private final com.example.demo_api.service.CotizacionService cotizacionService;
    private final com.example.demo_api.service.ScraperService scraperService;

    public CotizacionController(com.example.demo_api.service.CotizacionService cotizacionService, com.example.demo_api.service.ScraperService scraperService) {
        this.cotizacionService = cotizacionService;
        this.scraperService = scraperService;
    }

    @GetMapping(path = "/materiales/{id}/cotizaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO>> comparar(@PathVariable("id") String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.List.of());
        }
        var list = cotizacionService.compararPorMaterial(id);
        return ResponseEntity.ok(list);
    }

    @PutMapping(path = "/cotizaciones/{id}/precio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.ActualizarPrecioResponse> actualizarPrecio(@PathVariable("id") String id,
                                                                                            @RequestBody com.example.demo_api.dto.ActualizarPrecioRequest request) {
        if (id == null || id.isBlank() || request.getUrl() == null || request.getUrl().isBlank()) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.ActualizarPrecioResponse("Error", "id y url son requeridos", null, null));
        }
        try {
            var doc = scraperService.obtenerDocumento(request.getUrl());
            var precio = scraperService.extraerPrecioSeguro(doc);
            if (precio == null) {
                return ResponseEntity.badRequest().body(new com.example.demo_api.dto.ActualizarPrecioResponse("Error", "No se pudo extraer precio", id, null));
            }
            int updated = cotizacionService.actualizarPrecio(id, precio);
            if (updated > 0) {
                return ResponseEntity.ok(new com.example.demo_api.dto.ActualizarPrecioResponse("Exito", null, id, precio));
            }
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.ActualizarPrecioResponse("Error", "Cotizacion no encontrada", id, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.ActualizarPrecioResponse("Error", e.getMessage(), id, null));
        }
    }

    @PostMapping(path = "/cotizaciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.CrearCotizacionResponse> crear(@RequestBody com.example.demo_api.dto.CrearCotizacionRequest request) {
        boolean invalid = request.getIdMaterial() == null || request.getIdMaterial().isBlank()
                || request.getNombreProveedor() == null || request.getNombreProveedor().isBlank()
                || request.getNombreMaterial() == null || request.getNombreMaterial().isBlank()
                || request.getPrecioMaterial() == null || request.getPrecioMaterial().signum() <= 0
                || request.getEnlaceCompra() == null || request.getEnlaceCompra().isBlank();
        if (invalid) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.CrearCotizacionResponse("Error", null, "Campos inválidos"));
        }
        var resp = cotizacionService.crear(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.status(201).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping(path = "/cotizaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> listar() {
        return cotizacionService.listarTodas();
    }
}
