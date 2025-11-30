package com.example.demo_api.service;

import com.example.demo_api.repository.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public java.util.List<com.example.demo_api.dto.PersonaDTO> listarTodas() {
        try {
            return personaRepository.findAll().stream()
                    .map(persona -> {
                        var dto = new com.example.demo_api.dto.PersonaDTO();
                        dto.setIdUsuario(persona.getIdUsuario());
                        dto.setNombre(persona.getNombre());
                        dto.setEmail(persona.getEmail());
                        dto.setContacto(persona.getContacto());
                        dto.setTipoPersona(persona.getTipoPersona());
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
