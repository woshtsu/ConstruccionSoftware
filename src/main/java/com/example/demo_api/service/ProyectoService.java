package com.example.demo_api.service;

import com.example.demo_api.dao.ProyectoDao;
import com.example.demo_api.dto.RegistrarProyectoRequest;
import com.example.demo_api.dto.RegistrarProyectoResponse;
import org.springframework.stereotype.Service;

@Service
public class ProyectoService {
    private final ProyectoDao proyectoDao;

    public ProyectoService(ProyectoDao proyectoDao) {
        this.proyectoDao = proyectoDao;
    }

    public RegistrarProyectoResponse registrar(RegistrarProyectoRequest request) {
        try {
            String id = proyectoDao.registrarProyectoCompleto(
                    request.getIdCliente(),
                    request.getTitulo(),
                    request.getDescripcion(),
                    request.getIdAsesor()
            );
            return new RegistrarProyectoResponse("Exito", id, null);
        } catch (Exception e) {
            return new RegistrarProyectoResponse("Error", null, e.getMessage());
        }
    }

    public com.example.demo_api.dto.ProyectoDTO obtenerPorId(String idProyecto) {
        try {
            return proyectoDao.obtenerPorId(idProyecto);
        } catch (Exception e) {
            return null;
        }
    }
}
