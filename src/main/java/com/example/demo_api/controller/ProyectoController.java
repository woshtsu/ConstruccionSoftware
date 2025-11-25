package com.example.demo_api.controller;

import com.example.demo_api.dto.RegistrarProyectoRequest;
import com.example.demo_api.dto.RegistrarProyectoResponse;
import com.example.demo_api.service.ProyectoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final com.example.demo_api.service.AreaService areaService;
    private final com.example.demo_api.service.ProformaService proformaService;

    public ProyectoController(ProyectoService proyectoService, com.example.demo_api.service.AreaService areaService, com.example.demo_api.service.ProformaService proformaService) {
        this.proyectoService = proyectoService;
        this.areaService = areaService;
        this.proformaService = proformaService;
    }

    @GetMapping(path = "/hola", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> hola() {
        return Map.of("mensaje", "hola");
    }

    @PostMapping(path = "/proyectos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrarProyectoResponse> registrarProyecto(@RequestBody RegistrarProyectoRequest request) {
        if (request.getIdCliente() == null || request.getIdCliente().isBlank() || request.getTitulo() == null || request.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.RegistrarProyectoResponse("Error", null, "idCliente y titulo son requeridos"));
        }
        var resp = proyectoService.registrar(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.status(201).body(resp);
        }
        return ResponseEntity.badRequest().body(resp);
    }

    @GetMapping(path = "/proyectos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.ProyectoDTO> obtenerProyecto(@PathVariable("id") String id) {
        var dto = proyectoService.obtenerPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping(path = "/proyectos/{id}/areas", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.AreaDTO> listarAreas(@PathVariable("id") String id) {
        return areaService.listarPorProyecto(id);
    }

    @GetMapping(path = "/proyectos/{id}/proformas", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.ProformaDTO> listarProformas(@PathVariable("id") String id) {
        return proformaService.listarPorProyecto(id);
    }
}
