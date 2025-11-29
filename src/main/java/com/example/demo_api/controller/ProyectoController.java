package com.example.demo_api.controller;

import com.example.demo_api.dto.RegistrarProyectoRequest;
import com.example.demo_api.dto.RegistrarProyectoResponse;
import com.example.demo_api.service.ProyectoService;
import com.example.demo_api.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final com.example.demo_api.service.AreaService areaService;
    private final com.example.demo_api.service.ProformaService proformaService;
    private final AuthService authService;

    public ProyectoController(ProyectoService proyectoService, com.example.demo_api.service.AreaService areaService, com.example.demo_api.service.ProformaService proformaService, AuthService authService) {
        this.proyectoService = proyectoService;
        this.areaService = areaService;
        this.proformaService = proformaService;
        this.authService = authService;
    }

    @GetMapping(path = "/hola", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> hola() {
        return Map.of("mensaje", "hola");
    }

    @PostMapping(path = "/proyectos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrarProyectoResponse> registrarProyecto(@RequestBody RegistrarProyectoRequest request, HttpServletRequest http) {
        if ((request.getIdCliente() == null || request.getIdCliente().isBlank())) {
            String token = parseBearer(http.getHeader("Authorization"));
            if (token != null) {
                String id = authService.getClienteIdByToken(token);
                if (id != null) request.setIdCliente(id);
            }
        }
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

    @GetMapping(path = "/proyectos/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.ProyectoDTO> buscarPorNombrePersona(@RequestParam("nombre") String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return java.util.List.of();
        }
        return proyectoService.buscarPorNombrePersona(nombre);
    }

    @GetMapping(path = "/proyectos", produces = MediaType.APPLICATION_JSON_VALUE)
    public java.util.List<com.example.demo_api.dto.ProyectoDTO> listarTodos() {
        return proyectoService.listarTodos();
    }

    @DeleteMapping(path = "/proyectos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.Map<String, String>> eliminar(@PathVariable("id") String id) {
        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("estado", "Error", "mensaje", "id es requerido"));
        }
        boolean ok = proyectoService.eliminarPorId(id);
        if (ok) return ResponseEntity.ok(java.util.Map.of("estado", "Exito"));
        return ResponseEntity.notFound().build();
    }

    private String parseBearer(String authorization) {
        if (authorization == null) return null;
        String prefix = "Bearer ";
        if (authorization.startsWith(prefix)) return authorization.substring(prefix.length());
        return null;
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
