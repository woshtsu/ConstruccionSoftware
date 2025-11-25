package com.example.demo_api.service;

import com.example.demo_api.dao.MaterialProformaDao;
import com.example.demo_api.dao.ProformaDao;
import com.example.demo_api.dto.AgregarDetalleProformaRequest;
import com.example.demo_api.dto.AgregarDetalleProformaResponse;
import org.springframework.stereotype.Service;

@Service
public class MaterialProformaService {
    private final MaterialProformaDao materialProformaDao;
    private final ProformaDao proformaDao;

    public MaterialProformaService(MaterialProformaDao materialProformaDao, ProformaDao proformaDao) {
        this.materialProformaDao = materialProformaDao;
        this.proformaDao = proformaDao;
    }

    public AgregarDetalleProformaResponse agregar(AgregarDetalleProformaRequest request) {
        try {
            String idMp = materialProformaDao.agregarDetalle(
                    request.getIdProforma(),
                    request.getIdCotizacion(),
                    request.getIdAreaConstruccion(),
                    request.getCantidad()
            );
            var proforma = proformaDao.obtenerPorId(request.getIdProforma());
            java.math.BigDecimal total = proforma != null ? proforma.getCostoTotal() : null;
            return new AgregarDetalleProformaResponse("Exito", null, idMp, total);
        } catch (Exception e) {
            return new AgregarDetalleProformaResponse("Error", e.getMessage(), null, null);
        }
    }

    public java.util.List<com.example.demo_api.dto.MaterialProformaDTO> listarPorProforma(String idProforma) {
        try {
            return materialProformaDao.listarPorProforma(idProforma);
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
