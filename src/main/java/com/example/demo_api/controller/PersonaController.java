package com.example.demo_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonaController {
    private final com.example.demo_api.service.PersonaService personaService;

    public PersonaController(com.example.demo_api.service.PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping(path = "/personas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.List<com.example.demo_api.dto.PersonaDTO>> listarTodas() {
        return ResponseEntity.ok(personaService.listarTodas());
    }
}
