package com.example.demo_api.service;

import com.example.demo_api.model.Proyecto;
import com.example.demo_api.model.Servicio;
import com.example.demo_api.repository.ProyectoRepository;
import com.example.demo_api.dto.RegistrarProyectoRequest;
import com.example.demo_api.dto.RegistrarProyectoResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final ServicioService servicioService;

    public ProyectoService(ProyectoRepository proyectoRepository, ServicioService servicioService) {
        this.proyectoRepository = proyectoRepository;
        this.servicioService = servicioService;
    }

    public RegistrarProyectoResponse registrar(RegistrarProyectoRequest request) {
        try {
            // Crear servicio con cliente y asesor
            Servicio servicio = servicioService.crearServicio(
                    request.getIdCliente(),
                    request.getIdAsesor()
            );

            String idProyecto = UUID.randomUUID().toString();
            Proyecto proyecto = new Proyecto(
                    idProyecto,
                    servicio,
                    request.getTitulo(),
                    request.getDescripcion()
            );
            proyectoRepository.save(proyecto);
            return new RegistrarProyectoResponse("Exito", idProyecto, null);
        } catch (Exception e) {
            return new RegistrarProyectoResponse("Error", null, e.getMessage());
        }
    }

    public com.example.demo_api.dto.ProyectoDTO obtenerPorId(String idProyecto) {
        try {
            var proyecto = proyectoRepository.findById(idProyecto);
            if (proyecto.isEmpty()) {
                return null;
            }
            var dto = new com.example.demo_api.dto.ProyectoDTO();
            dto.setIdProyecto(proyecto.get().getIdProyecto());
            dto.setIdServicio(proyecto.get().getServicio() != null ? proyecto.get().getServicio().getIdServicio() : null);
            dto.setIdReporte(proyecto.get().getReporte() != null ? proyecto.get().getReporte().getIdReporte() : null);
            dto.setTitulo(proyecto.get().getTitulo());
            dto.setDescripcion(proyecto.get().getDescripcion());
            dto.setFechaCreacion(java.sql.Timestamp.valueOf(proyecto.get().getFechaCreacion()));
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    public java.util.List<com.example.demo_api.dto.ProyectoDTO> buscarPorNombrePersona(String nombre) {
        try {
            // TODO: Implementar búsqueda por nombre de persona cuando esté disponible el repositorio
            return java.util.List.of();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public java.util.List<com.example.demo_api.dto.ProyectoDTO> listarTodos() {
        try {
            return proyectoRepository.findAll()
                    .stream()
                    .map(proyecto -> {
                        var dto = new com.example.demo_api.dto.ProyectoDTO();
                        dto.setIdProyecto(proyecto.getIdProyecto());
                        dto.setIdServicio(proyecto.getServicio() != null ? proyecto.getServicio().getIdServicio() : null);
                        dto.setIdReporte(proyecto.getReporte() != null ? proyecto.getReporte().getIdReporte() : null);
                        dto.setTitulo(proyecto.getTitulo());
                        dto.setDescripcion(proyecto.getDescripcion());
                        dto.setFechaCreacion(java.sql.Timestamp.valueOf(proyecto.getFechaCreacion()));
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public boolean eliminarPorId(String idProyecto) {
        try {
            proyectoRepository.deleteById(idProyecto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
