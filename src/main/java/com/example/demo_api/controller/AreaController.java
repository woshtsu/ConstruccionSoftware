package com.example.demo_api.controller;

import com.example.demo_api.dto.CrearAreaRequest;
import com.example.demo_api.dto.CrearAreaResponse;
import com.example.demo_api.service.AreaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AreaController {
    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping(path = "/areas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrearAreaResponse> crear(@RequestBody CrearAreaRequest request) {
        boolean invalid = request.getIdProyecto() == null || request.getIdProyecto().isBlank()
                || request.getTipo() == null || request.getTipo().isBlank()
                || request.getLargo() == null || request.getLargo().signum() <= 0
                || request.getAlto() == null || request.getAlto().signum() <= 0
                || request.getEspesor() == null || request.getEspesor().signum() <= 0
                || request.getSuperficie() == null || request.getSuperficie().signum() <= 0;
        if (invalid) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.CrearAreaResponse("Error", null, "Campos inválidos"));
        }
        var resp = areaService.crear(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.status(201).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping(path = "/areas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.AreaDTO> obtener(@PathVariable("id") String id) {
        var dto = areaService.obtenerPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}
