package com.example.demo_api.service;

import com.example.demo_api.model.Servicio;
import com.example.demo_api.model.Persona;
import com.example.demo_api.model.Asesor;
import com.example.demo_api.repository.ServicioRepository;
import com.example.demo_api.repository.PersonaRepository;
import com.example.demo_api.repository.AsesorRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServicioService {
    private final ServicioRepository servicioRepository;
    private final PersonaRepository personaRepository;
    private final AsesorRepository asesorRepository;

    public ServicioService(ServicioRepository servicioRepository, PersonaRepository personaRepository, AsesorRepository asesorRepository) {
        this.servicioRepository = servicioRepository;
        this.personaRepository = personaRepository;
        this.asesorRepository = asesorRepository;
    }

    public Servicio crearServicio(String idCliente, String idAsesor) {
        try {
            var cliente = personaRepository.findById(idCliente);
            if (cliente.isEmpty()) {
                throw new RuntimeException("Cliente no encontrado");
            }

            Asesor asesor = null;
            if (idAsesor != null && !idAsesor.isBlank()) {
                var asesorOpt = asesorRepository.findById(idAsesor);
                if (asesorOpt.isPresent()) {
                    asesor = asesorOpt.get();
                }
            }

            String idServicio = UUID.randomUUID().toString();
            Servicio servicio = new Servicio(idServicio, cliente.get(), asesor);
            return servicioRepository.save(servicio);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear servicio: " + e.getMessage());
        }
    }

    public Servicio obtenerServicio(String idServicio) {
        return servicioRepository.findById(idServicio).orElse(null);
    }
}
