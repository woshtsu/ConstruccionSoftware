package com.example.demo_api.service;

import com.example.demo_api.dao.CotizacionDao;
import org.springframework.stereotype.Service;

@Service
public class CotizacionService {
    private final CotizacionDao cotizacionDao;
    private final com.example.demo_api.service.ScraperService scraperService;

    public CotizacionService(CotizacionDao cotizacionDao, com.example.demo_api.service.ScraperService scraperService) {
        this.cotizacionDao = cotizacionDao;
        this.scraperService = scraperService;
    }

    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> compararPorMaterial(String idMaterial) {
        try {
            return cotizacionDao.compararPorMaterial(idMaterial);
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public int actualizarPrecio(String idCotizacion, java.math.BigDecimal precio) {
        try {
            return cotizacionDao.actualizarPrecio(idCotizacion, precio);
        } catch (Exception e) {
            return 0;
        }
    }

    public com.example.demo_api.dto.CrearCotizacionResponse crear(com.example.demo_api.dto.CrearCotizacionRequest request) {
        try {
            String id = cotizacionDao.crear(
                    request.getIdMaterial(),
                    request.getNombreProveedor(),
                    request.getNombreMaterial(),
                    request.getPrecioMaterial(),
                    request.getEnlaceCompra()
            );
            return new com.example.demo_api.dto.CrearCotizacionResponse("Exito", id, null);
        } catch (Exception e) {
            return new com.example.demo_api.dto.CrearCotizacionResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.CotizacionComparacionDTO> listarTodas() {
        try {
            return cotizacionDao.listarTodas();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
