package com.example.demo_api.service;

import com.example.demo_api.dao.UsuarioDao;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final UsuarioDao usuarioDao;
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public AuthService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public com.example.demo_api.dto.LoginResponse login(com.example.demo_api.dto.LoginRequest request) {
        try {
            String id = usuarioDao.autenticar(request.getEmail(), request.getPassword());
            if (id != null) {
                String token = UUID.randomUUID().toString();
                tokenStore.put(token, id);
                return new com.example.demo_api.dto.LoginResponse("Exito", null, token, id);
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
