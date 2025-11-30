package com.example.demo_api.service;

import com.example.demo_api.model.Proforma;
import com.example.demo_api.model.Proyecto;
import com.example.demo_api.repository.ProformaRepository;
import com.example.demo_api.repository.ProyectoRepository;
import com.example.demo_api.dto.CrearProformaRequest;
import com.example.demo_api.dto.CrearProformaResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProformaService {
    private final ProformaRepository proformaRepository;
    private final ProyectoRepository proyectoRepository;

    public ProformaService(ProformaRepository proformaRepository, ProyectoRepository proyectoRepository) {
        this.proformaRepository = proformaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public CrearProformaResponse crear(CrearProformaRequest request) {
        try {
            String id = UUID.randomUUID().toString();
            var proyectoOpt = proyectoRepository.findById(request.getIdProyecto());
            if (proyectoOpt.isEmpty()) {
                return new CrearProformaResponse("Error", null, "Proyecto no encontrado");
            }
            Proyecto proyecto = proyectoOpt.get();
            Proforma proforma = new Proforma(id, proyecto, java.math.BigDecimal.ZERO);
            proformaRepository.save(proforma);
            return new CrearProformaResponse("Exito", id, null);
        } catch (Exception e) {
            return new CrearProformaResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.ProformaDTO> listarPorProyecto(String idProyecto) {
        try {
            return proformaRepository.findByProyecto_IdProyecto(idProyecto)
                    .stream()
                    .map(proforma -> {
                        var dto = new com.example.demo_api.dto.ProformaDTO();
                        dto.setIdProforma(proforma.getIdProforma());
                        dto.setIdProyecto(proforma.getProyecto().getIdProyecto());
                        dto.setCostoTotal(proforma.getCostoTotal());
                        dto.setFecha(java.sql.Timestamp.valueOf(proforma.getFechaCreacion()));
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public com.example.demo_api.dto.ProformaDTO obtenerPorId(String idProforma) {
        try {
            var proforma = proformaRepository.findById(idProforma);
            if (proforma.isEmpty()) {
                return null;
            }
            var dto = new com.example.demo_api.dto.ProformaDTO();
            dto.setIdProforma(proforma.get().getIdProforma());
            dto.setIdProyecto(proforma.get().getProyecto().getIdProyecto());
            dto.setCostoTotal(proforma.get().getCostoTotal());
            dto.setFecha(java.sql.Timestamp.valueOf(proforma.get().getFechaCreacion()));
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean eliminarPorId(String idProforma) {
        try {
            proformaRepository.deleteById(idProforma);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
