package com.example.demo_api.service;

import com.example.demo_api.model.AreaConstruccion;
import com.example.demo_api.repository.AreaConstruccionRepository;
import com.example.demo_api.repository.ProyectoRepository;
import com.example.demo_api.dto.CrearAreaRequest;
import com.example.demo_api.dto.CrearAreaResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AreaService {
    private final AreaConstruccionRepository areaConstruccionRepository;
    private final ProyectoRepository proyectoRepository;

    public AreaService(AreaConstruccionRepository areaConstruccionRepository, ProyectoRepository proyectoRepository) {
        this.areaConstruccionRepository = areaConstruccionRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public CrearAreaResponse crear(CrearAreaRequest request) {
        try {
            var proyecto = proyectoRepository.findById(request.getIdProyecto());
            if (proyecto.isEmpty()) {
                return new CrearAreaResponse("Error", null, "Proyecto no encontrado");
            }

            String id = UUID.randomUUID().toString();
            AreaConstruccion area = new AreaConstruccion(
                    id,
                    proyecto.get(),
                    request.getTipo(),
                    request.getLargo(),
                    request.getAlto(),
                    request.getEspesor(),
                    request.getSuperficie(),
                    request.getArchivoImportado()
            );
            areaConstruccionRepository.save(area);
            return new CrearAreaResponse("Exito", id, null);
        } catch (Exception e) {
            return new CrearAreaResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.AreaDTO> listarPorProyecto(String idProyecto) {
        try {
            var proyecto = proyectoRepository.findById(idProyecto);
            if (proyecto.isEmpty()) {
                return java.util.List.of();
            }
            return areaConstruccionRepository.findByProyecto(proyecto.get())
                    .stream()
                    .map(area -> {
                        var dto = new com.example.demo_api.dto.AreaDTO();
                        dto.setIdAreaConstruccion(area.getIdAreaConstruccion());
                        dto.setIdProyecto(area.getProyecto().getIdProyecto());
                        dto.setTipo(area.getTipo());
                        dto.setLargo(area.getLargo());
                        dto.setAlto(area.getAlto());
                        dto.setEspesor(area.getEspesor());
                        dto.setSuperficie(area.getSuperficie());
                        dto.setArchivoImportado(area.getArchivoImportado());
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public com.example.demo_api.dto.AreaDTO obtenerPorId(String idArea) {
        try {
            var area = areaConstruccionRepository.findById(idArea);
            if (area.isEmpty()) {
                return null;
            }
            var dto = new com.example.demo_api.dto.AreaDTO();
            dto.setIdAreaConstruccion(area.get().getIdAreaConstruccion());
            dto.setIdProyecto(area.get().getProyecto().getIdProyecto());
            dto.setTipo(area.get().getTipo());
            dto.setLargo(area.get().getLargo());
            dto.setAlto(area.get().getAlto());
            dto.setEspesor(area.get().getEspesor());
            dto.setSuperficie(area.get().getSuperficie());
            dto.setArchivoImportado(area.get().getArchivoImportado());
            return dto;
        } catch (Exception e) {
            return null;
        }
    }
}
