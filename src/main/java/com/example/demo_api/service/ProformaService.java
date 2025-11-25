package com.example.demo_api.service;

import com.example.demo_api.dao.ProformaDao;
import com.example.demo_api.dto.CrearProformaRequest;
import com.example.demo_api.dto.CrearProformaResponse;
import org.springframework.stereotype.Service;

@Service
public class ProformaService {
    private final ProformaDao proformaDao;

    public ProformaService(ProformaDao proformaDao) {
        this.proformaDao = proformaDao;
    }

    public CrearProformaResponse crear(CrearProformaRequest request) {
        try {
            String id = proformaDao.crear(request.getIdProyecto());
            return new CrearProformaResponse("Exito", id, null);
        } catch (Exception e) {
            return new CrearProformaResponse("Error", null, e.getMessage());
        }
    }

    public java.util.List<com.example.demo_api.dto.ProformaDTO> listarPorProyecto(String idProyecto) {
        try {
            return proformaDao.listarPorProyecto(idProyecto);
        } catch (Exception e) {
            return java.util.List.of();
        }
    }

    public com.example.demo_api.dto.ProformaDTO obtenerPorId(String idProforma) {
        try {
            return proformaDao.obtenerPorId(idProforma);
        } catch (Exception e) {
            return null;
        }
    }
}
