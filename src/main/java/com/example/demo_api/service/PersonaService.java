package com.example.demo_api.service;

import com.example.demo_api.dao.PersonaDao;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private final PersonaDao personaDao;

    public PersonaService(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public java.util.List<com.example.demo_api.dto.PersonaDTO> listarTodas() {
        try {
            return personaDao.listarTodas();
        } catch (Exception e) {
            return java.util.List.of();
        }
    }
}
