package com.example.demo_api.service;

import com.example.demo_api.dao.ReporteDao;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {
    private final ReporteDao reporteDao;

    public ReporteService(ReporteDao reporteDao) {
        this.reporteDao = reporteDao;
    }

    public java.util.List<com.example.demo_api.dto.ReporteDTO> listarPorPersona(String idPersona) {
        try {
            return reporteDao.listarPorPersona(idPersona);
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
