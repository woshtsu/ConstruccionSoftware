package com.example.demo_api.service;

import com.example.demo_api.dao.AreaDao;
import com.example.demo_api.dto.CrearAreaRequest;
import com.example.demo_api.dto.CrearAreaResponse;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    private final AreaDao areaDao;

    public AreaService(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    public CrearAreaResponse crear(CrearAreaRequest request) {
        try {
            String id = areaDao.crear(
                    request.getIdProyecto(),
                    request.getTipo(),
                    request.getLargo(),
                    request.getAlto(),
                    request.getEspesor(),
                    request.getSuperficie(),
                    request.getArchivoImportado()
            );
            return new CrearAreaResponse("Exito", id, null);
        } catch (Exception e) {
            return new CrearAreaResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.AreaDTO> listarPorProyecto(String idProyecto) {
        try {
            return areaDao.listarPorProyecto(idProyecto);
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public com.example.demo_api.dto.AreaDTO obtenerPorId(String idArea) {
        try {
            return areaDao.obtenerPorId(idArea);
        } catch (Exception e) {
            return null;
        }
    }
}
