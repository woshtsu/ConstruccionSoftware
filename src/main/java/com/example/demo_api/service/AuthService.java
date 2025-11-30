package com.example.demo_api.service;

import com.example.demo_api.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final PersonaRepository personaRepository;
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public AuthService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public com.example.demo_api.dto.LoginResponse login(com.example.demo_api.dto.LoginRequest request) {
        try {
            var persona = personaRepository.findByEmail(request.getEmail());
            if (persona.isPresent() && persona.get().getPassword().equals(request.getPassword())) {
                String token = UUID.randomUUID().toString();
                tokenStore.put(token, persona.get().getIdUsuario());
                return new com.example.demo_api.dto.LoginResponse("Exito", null, token, persona.get().getIdUsuario());
            }
            return new com.example.demo_api.dto.LoginResponse("Error", "Credenciales inválidas", null, null);
        } catch (Exception e) {
            return new com.example.demo_api.dto.LoginResponse("Error", e.getMessage(), null, null);
        }
    }

    public boolean logout(String token) {
        return tokenStore.remove(token) != null;
    }

    public String getClienteIdByToken(String token) {
        return tokenStore.get(token);
    }
}
