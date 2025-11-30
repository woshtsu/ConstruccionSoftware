package com.example.demo_api.service;

import com.example.demo_api.model.MaterialProforma;
import com.example.demo_api.model.Cotizacion;
import com.example.demo_api.model.Proforma;
import com.example.demo_api.repository.ProformaRepository;
import com.example.demo_api.repository.CotizacionRepository;
import com.example.demo_api.repository.MaterialProformaRepository;
import com.example.demo_api.dto.AgregarDetalleProformaRequest;
import com.example.demo_api.dto.AgregarDetalleProformaResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MaterialProformaService {
    private final CotizacionRepository cotizacionRepository;
    private final ProformaRepository proformaRepository;
    private final MaterialProformaRepository materialProformaRepository;

    public MaterialProformaService(CotizacionRepository cotizacionRepository, ProformaRepository proformaRepository, MaterialProformaRepository materialProformaRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.proformaRepository = proformaRepository;
        this.materialProformaRepository = materialProformaRepository;
    }

    public AgregarDetalleProformaResponse agregar(AgregarDetalleProformaRequest request) {
        try {
            var proforma = proformaRepository.findById(request.getIdProforma());
            var cotizacion = cotizacionRepository.findById(request.getIdCotizacion());
            
            if (proforma.isEmpty() || cotizacion.isEmpty()) {
                return new AgregarDetalleProformaResponse("Error", "Proforma o Cotización no encontrada", null, null);
            }

            String idDetalle = UUID.randomUUID().toString();
            java.math.BigDecimal subtotal = cotizacion.get().getPrecioMaterial()
                    .multiply(request.getCantidad());
            
            MaterialProforma detalle = new MaterialProforma(
                    idDetalle,
                    proforma.get(),
                    cotizacion.get(),
                    request.getCantidad(),
                    subtotal
            );

                // Actualizar el costo total de la proforma
                materialProformaRepository.save(detalle);
                proforma.get().setCostoTotal(
                    proforma.get().getCostoTotal().add(subtotal)
            );
            
            proformaRepository.save(proforma.get());
            
            return new AgregarDetalleProformaResponse("Exito", null, idDetalle, proforma.get().getCostoTotal());
        } catch (Exception e) {
            return new AgregarDetalleProformaResponse("Error", e.getMessage(), null, null);
        }
    }

    public java.util.List<com.example.demo_api.dto.MaterialProformaDTO> listarPorProforma(String idProforma) {
        try {
            var proforma = proformaRepository.findById(idProforma);
            if (proforma.isEmpty()) {
                return java.util.List.of();
            }
            return proforma.get().getMateriales()
                    .stream()
                    .map(detalle -> {
                        var dto = new com.example.demo_api.dto.MaterialProformaDTO();
                        dto.setIdMaterialProforma(detalle.getIdMaterialProforma());
                        dto.setIdProforma(detalle.getProforma().getIdProforma());
                        dto.setIdCotizacion(detalle.getCotizacion().getIdCotizacion());
                        dto.setCantidad(detalle.getCantidad());
                        dto.setSubtotal(detalle.getSubtotal());
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
