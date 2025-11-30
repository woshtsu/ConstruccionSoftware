package com.example.demo_api.service;

import com.example.demo_api.model.Cotizacion;
import com.example.demo_api.repository.CotizacionRepository;
import com.example.demo_api.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CotizacionService {
    private final CotizacionRepository cotizacionRepository;
    private final MaterialRepository materialRepository;

    public CotizacionService(CotizacionRepository cotizacionRepository, MaterialRepository materialRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.materialRepository = materialRepository;
    }

    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> compararPorMaterial(String idMaterial) {
        try {
            var material = materialRepository.findById(idMaterial);
            if (material.isEmpty()) {
                return java.util.List.of();
            }
            return cotizacionRepository.findByMaterial(material.get())
                    .stream()
                    .map(cot -> {
                        var dto = new com.example.demo_api.dto.CotizacionComparacionDTO();
                        dto.setIdCotizacion(cot.getIdCotizacion());
                        dto.setNombreProveedor(cot.getNombreProveedor());
                        dto.setNombreMaterial(cot.getNombreMaterial());
                        dto.setPrecioMaterial(cot.getPrecioMaterial());
                        dto.setEnlaceCompra(cot.getEnlaceCompra());
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public int actualizarPrecio(String idCotizacion, java.math.BigDecimal precio) {
        try {
            var cotizacion = cotizacionRepository.findById(idCotizacion);
            if (cotizacion.isEmpty()) {
                return 0;
            }
            cotizacion.get().setPrecioMaterial(precio);
            cotizacionRepository.save(cotizacion.get());
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public com.example.demo_api.dto.CrearCotizacionResponse crear(com.example.demo_api.dto.CrearCotizacionRequest request) {
        try {
            var material = materialRepository.findById(request.getIdMaterial());
            if (material.isEmpty()) {
                return new com.example.demo_api.dto.CrearCotizacionResponse("Error", null, "Material no encontrado");
            }

            String id = UUID.randomUUID().toString();
            Cotizacion cotizacion = new Cotizacion(
                    id,
                    material.get(),
                    request.getNombreProveedor(),
                    request.getNombreMaterial(),
                    request.getPrecioMaterial(),
                    request.getEnlaceCompra()
            );
            cotizacionRepository.save(cotizacion);
            return new com.example.demo_api.dto.CrearCotizacionResponse("Exito", id, null);
        } catch (Exception e) {
            return new com.example.demo_api.dto.CrearCotizacionResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> listarTodas() {
        try {
            return cotizacionRepository.findAll()
                    .stream()
                    .map(cot -> {
                        var dto = new com.example.demo_api.dto.CotizacionComparacionDTO();
                        dto.setIdCotizacion(cot.getIdCotizacion());
                        dto.setNombreProveedor(cot.getNombreProveedor());
                        dto.setNombreMaterial(cot.getNombreMaterial());
                        dto.setPrecioMaterial(cot.getPrecioMaterial());
                        dto.setEnlaceCompra(cot.getEnlaceCompra());
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
