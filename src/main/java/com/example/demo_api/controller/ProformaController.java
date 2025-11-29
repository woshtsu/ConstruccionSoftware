package com.example.demo_api.controller;

import com.example.demo_api.dto.CrearProformaRequest;
import com.example.demo_api.dto.CrearProformaResponse;
import com.example.demo_api.service.ProformaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProformaController {
    private final ProformaService proformaService;
    private final com.example.demo_api.service.MaterialProformaService materialProformaService;

    public ProformaController(ProformaService proformaService, com.example.demo_api.service.MaterialProformaService materialProformaService) {
        this.proformaService = proformaService;
        this.materialProformaService = materialProformaService;
    }

    @PostMapping(path = "/proformas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrearProformaResponse> crear(@RequestBody CrearProformaRequest request) {
        if (request.getIdProyecto() == null || request.getIdProyecto().isBlank()) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.CrearProformaResponse("Error", null, "idProyecto es requerido"));
        }
        var resp = proformaService.crear(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.status(201).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping(path = "/proformas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.ProformaDTO> obtener(@PathVariable("id") String id) {
        var dto = proformaService.obtenerPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping(path = "/proformas/detalles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.AgregarDetalleProformaResponse> agregarDetalle(@RequestBody com.example.demo_api.dto.AgregarDetalleProformaRequest request) {
        boolean invalid = request.getIdProforma() == null || request.getIdProforma().isBlank()
                || request.getIdCotizacion() == null || request.getIdCotizacion().isBlank()
                || request.getIdAreaConstruccion() == null || request.getIdAreaConstruccion().isBlank()
                || request.getCantidad() == null || request.getCantidad().signum() <= 0;
        if (invalid) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.AgregarDetalleProformaResponse("Error", "Campos inválidos", null, null));
        }
        var resp = materialProformaService.agregar(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.status(201).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping(path = "/proformas/{id}/detalles", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.MaterialProformaDTO> listarDetalles(@PathVariable("id") String id) {
        return materialProformaService.listarPorProforma(id);
    }

    @DeleteMapping(path = "/proformas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.Map<String, String>> eliminar(@PathVariable("id") String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("estado", "Error", "mensaje", "id es requerido"));
        }
        boolean ok = proformaService.eliminarPorId(id);
        if (ok) return ResponseEntity.ok(java.util.Map.of("estado", "Exito"));
        return ResponseEntity.notFound().build();
    }
}
